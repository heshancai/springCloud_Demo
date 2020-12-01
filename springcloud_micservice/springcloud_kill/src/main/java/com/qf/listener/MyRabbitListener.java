package com.qf.listener;

import com.qf.entity.GoodsEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费队列的消息
 * 生成就静态页
 */
@Component
public class MyRabbitListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Configuration configuration;

    //绑定队列消费信息
    @RabbitListener(queues = "kill_queue")
    public void msgHandle(GoodsEntity goodsEntity){
        System.out.println("接收到队列的消息"+goodsEntity);

        //TODO 保存秒杀商品的库存到redis中用作秒杀的时候库存的判断
        redisTemplate.opsForValue().set("gsave_"+goodsEntity.getId(),goodsEntity.getGoodsSecondKillEntity().getKillSave()+"");

        //获取classpath路径 当前类的获取 静态页存放的路径
        String path = MyRabbitListener.class.getResource("/").getPath() + "static/html";
        System.out.println("静态页存放的位置:"+path);
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        //生成html文件
        file=new File(file,goodsEntity.getId()+".html");
        //准备一个静态的页的输出路径
        //字符输出流
        try (Writer writer=new FileWriter(file);){

            //获得对应的模板
            Template template = configuration.getTemplate("kill.ftlh");

            //准备传入模板的数据
            Map<String,Object> map=new HashMap<>();
            map.put("goods",goodsEntity);

            //生成静态页
            template.process(map,writer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
