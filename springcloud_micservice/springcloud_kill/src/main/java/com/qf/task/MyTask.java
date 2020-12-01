package com.qf.task;

import com.alibaba.fastjson.JSON;
import com.qf.entity.GoodsEntity;
import com.qf.entity.WebSocketMsgEntity;
import com.qf.feign.GoodsFeign;
import com.qf.util.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 定时器，定时更新抢购的场次
 * 作为防止提前下单条件判断
 */
@Component
public class MyTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 定时操作的方法
     *  cron表达式：秒 分 时 日 月 [星期] 年
     *  0/1 整除 正点触发更新时间场次
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void mytask(){
        System.out.println("定时器触发了，更详了秒杀场次的时间");
        //------------------------TODO 更新当前的秒杀场次--------------------------------
        //获取原来的场次,并且删除
        String oldTime = redisTemplate.opsForValue().get("killgoods_now");
        redisTemplate.delete("killgoods_"+oldTime);

        //更新redis中的场次时间
        //获取当前时间
        String nowTime = DateUtil.dateToString(new Date(), "yyMMddHH");
        redisTemplate.opsForValue().set("killgoods_now",nowTime );

    }

    /**
     * 秒杀提醒的功能
     * 每小时50分的时候进行提醒
     */
    @Scheduled(cron = "0 50 * * * *")
    public void mytask2(){

        System.out.println("触发了秒杀提醒");
        //获取当前的时间
        Date now=new Date();
        //获取当前时间的年月茹
        String yyMMdd = DateUtil.dateToString(now, "yyMMdd");
        //获取当前时间的时分
        String hhmm = DateUtil.dateToString(now, "HHmm");

        //查询redis找到需要秒杀的信息
        while (true) {
            //查询redis有序集合的信息  根据评分获取信息
            Set<String> content = redisTemplate.opsForZSet().rangeByScore("tixing_" + yyMMdd, Double.valueOf(hhmm), Double.valueOf(hhmm), 0, 100);
            //根据key和内容进行删除集合中的消息
            redisTemplate.opsForZSet().remove("tixing_"+yyMMdd,content.toArray());

            //结束循环
            if(content==null || content.size()==0){
                break;
            }

            //循环推送消息
            for (String c : content) {
                //将json字符串转为对象
                Map map=JSON.parseObject(c, HashMap.class);
                //获取商品的id
                Integer gid = (Integer) map.get("gid");

                //查询商品的信息
                GoodsEntity goodsEntity = goodsFeign.queryByGid(gid);

                map.put("goods",goodsEntity);

                //将消息封装成固定的消息对象
                WebSocketMsgEntity webSocketMsgEntity=new WebSocketMsgEntity()
                        .setFromId(-1)
                        .setToId((Integer) map.get("uid"))
                        .setType(3)
                        .setData(map);

                //将消息对象推送给客户端
                rabbitTemplate.convertAndSend("netty_exchange","",webSocketMsgEntity);
            }
        }
    }
}
