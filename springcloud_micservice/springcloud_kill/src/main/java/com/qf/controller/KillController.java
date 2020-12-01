package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.GoodsEntity;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.util.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/kill")
public class KillController {

    //调用商品微服务
    @Autowired
    private GoodsFeign goodsFeign;

    //注入验证码的生成对象
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //判断商品库存的lua脚本
   private String lua="--获得上传的商品id主键参数\n" +
            "local gid=KEYS[1]\n" +
            "--获得抢购商品的数量\n" +
            "local gnumber=tonumber(ARGV[1])\n" +
            "--获得上传用户id\n" +
            "local uid=ARGV[2]\n" +
            "--获得上传的当前时间\n" +
            "local now=ARGV[3]\n" +
            "--获得抢购当前商品的库存\n" +
            "local gsave=tonumber(redis.call('get','gsave_'..gid))\n" +
            "--判断库存\n" +
            "if gsave < gnumber then\n" +
            "--库存不足\n" +
            "return -1\n" +
            "end\n" +
            "--修改库存 自动减\n" +
            "local nowSave=tonumber(redis.call('decrby','gsave_'..gid,gnumber))\n" +
            "--记录排队的位置 按照时间进行添加到有序集合中\n" +
            "redis.call('zadd','paidui_'..gid,now,uid)\n" +
            "--抢购成功过\n" +
            "return 1";
    /**
     * 查询实时秒杀的时间
     * @return
     */
    @RequestMapping("/queryKillTimes")
    @ResponseBody
    public ResultData<List<Date>> queryKillTimes(){
        List<Date> dateList=new ArrayList<>();

        //分别获取秒杀的三个时间段
        Date now = DateUtil.getNextNDate(0);
        //下一小时
        Date nextDate = DateUtil.getNextNDate(1);
        Date nextDate1 = DateUtil.getNextNDate(2);
        dateList.add(now);
        dateList.add(nextDate);
        dateList.add(nextDate1);

        //返回服务器查到的当前时间
        return  new ResultData<List<Date>>().setCode(ResultData.ResultCodeList.OK).setData(dateList);

    }

    /**
     * 查询对应场次的秒杀商品列表
     * @return
     */
    @RequestMapping("/queryKillList")
    @ResponseBody
    public ResultData<List<GoodsEntity>> queryKillList(Integer n){

        //获取对应的时间   0  1  2
        //得到当前时间的整数点 以及 后一个小时 和后两个小时
        Date nextDate = DateUtil.getNextNDate(n);

        //根据时间查询对应的场次的商品信息
        List<GoodsEntity> list=goodsFeign.queryKillList(nextDate);

        return new ResultData<List<GoodsEntity>>().setCode(ResultData.ResultCodeList.OK).setData(list);
    }


    /**
     * 获取服务器的当前时间
     */
    @RequestMapping("/queryNow")
    @ResponseBody
    public ResultData<Date> queryNow(){
        System.out.println("查询了时间");
        return new ResultData<Date>().setCode(ResultData.ResultCodeList.OK).setData(new Date());
    }


    /**
     * 立即抢购的方法
     * @param gid
     * @param gnumber
     * @param model
     * @return
     */
    @RequestMapping("/qiangGou")
    @IsLogin(mustLoign =true)
    public String qiangGou(Integer gid, Integer gnumber, Model model){
        //判断发过来的数量
        if(gnumber==null || gnumber==0){
            gnumber=1;
        }

        //获取用户的信息
        User user = UserHolder.getUser();
        System.out.println(user.getNickname()+"抢购了"+gid);


        //执行lua脚本进行判断库存
        Long result = redisTemplate.execute(new DefaultRedisScript<>(lua, Long.class),
                Collections.singletonList(gid + ""),
                gnumber + "",
                user.getId() + "",
                System.currentTimeMillis() + "");

        //按判断是否抢购成功
        if(result!=-1){
            //抢购成功
            Map<String,Object> map=new HashMap<>();
            map.put("uid",user.getId());
            map.put("gnumber",gnumber);
            map.put("gid",gid);
            rabbitTemplate.convertAndSend("kill_goods_exchange","",map);

            model.addAttribute("gid",gid);
            //抢购成功
            return "paidui";

        }

        return "fail";
    }


    /**
     * 展示抢购排队信息的功能
     * @param gid
     * @return
     */
    @RequestMapping("/getbank")
    @ResponseBody
    @IsLogin(mustLoign = true)
    public ResultData<String> getBank(Integer gid){

        //获得当前用户信息
        User user = UserHolder.getUser();

        //获得排队的位置
        Long rank = redisTemplate.opsForZSet().rank("paidui_" + gid, user.getId() + "");


        System.out.println("当前用户的排名:"+rank);
        if(rank==null){
            //没有排名去，抢购成功
            return  new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData("抢购成功");

        }
        //当前正在排队
        return new ResultData<String>().setCode(ResultData.ResultCodeList.ERROR).setData(rank+1+"");
    }



    /**
     * 获取验证码和实时更新验证码
     */
    @ResponseBody
    @RequestMapping("/code")
    public void getCode(HttpServletResponse response){
        //获取验证码随机生成的文本
        String text = defaultKaptcha.createText();
        //由文本生成验证码的图片
        BufferedImage image = defaultKaptcha.createImage(text);
        //uuid作为key值
        String token = UUID.randomUUID().toString();
        //将验证码的值存入redis中做验证校验
        redisTemplate.opsForValue().set(token,text);
        //设置token的有效值时间
        redisTemplate.expire(token,1, TimeUnit.MINUTES);
        //每个用户生成唯一标识uuid
        Cookie cookie=new Cookie("codeToken",token);
        cookie.setPath("/");
        //cookie的有效期时间
        cookie.setMaxAge(60);
        //将key值存到浏览器中
        response.addCookie(cookie);
        //将验证码的图片输出到客户端中
        try {
            ImageIO.write(image,"jpg",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 秒杀提醒的功能
     * @param gid 商品id
     * @param flag 用来判断提醒和取消提醒的参数
     * @return
     */
    @RequestMapping("/tixing")
    @ResponseBody
    @IsLogin(mustLoign = true)
    public ResultData<String> tiXing(Integer gid,Integer flag){

        //获取用户的信息
        User user = UserHolder.getUser();

        //获取商品的秒杀时间
        GoodsEntity goodsEntity = goodsFeign.queryByGid(gid);

        Date startTime = goodsEntity.getGoodsSecondKillEntity().getStartTime();
        //获取商品秒杀时间的前十分钟
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.MINUTE,-10);
        //得到提醒的时间
        Date tiXingTime = calendar.getTime();

        //秒杀开始的时间年月日
        String yyMMdd = DateUtil.dateToString(tiXingTime, "yyMMdd");
        //秒杀开始时间的时分
        String hhmm = DateUtil.dateToString(tiXingTime, "HHmm");


        //设置提醒的内容
        Map<String,Integer> map=new HashMap<>();
        map.put("gid",gid);
        map.put("uid",user.getId());
        //将提醒的内容放到有序 集合中
        if(flag==1){
            //设置提醒 Double.valueOf(hhmm):评分
            redisTemplate.opsForZSet().add("tixing_"+yyMMdd, JSON.toJSONString(map),Double.valueOf(hhmm));
        }else {

            //取消提醒  根据内容进行删除集中的指定元素
            redisTemplate.opsForZSet().remove("tixing_"+yyMMdd,JSON.toJSONString(map));
        }

        return  new ResultData<String>().setCode(ResultData.ResultCodeList.OK);


    }
}
