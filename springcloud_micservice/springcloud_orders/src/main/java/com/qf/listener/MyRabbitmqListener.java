package com.qf.listener;

import com.qf.serviceImpl.IOrdersService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消费抢购商品的队列的消息
 * 生成订单
 */
@Component
public class MyRabbitmqListener {

    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //队列声明和交换机声明以及绑定
    @RabbitListener(
            //绑定
            bindings = @QueueBinding(
                    //声明交换机
                    exchange = @Exchange(type = "fanout",name = "kill_goods_exchange"),
                    //声明队列
                    value = @Queue(name = "kill_orders_queue",durable="true")
            )
    )
    public void killMsgHandler(Map<String,Object> map, Channel channel, Message message){
        System.out.println("接收到队列的消息:"+map);

        //拿到生成订单需要的参数
        Integer gid = (Integer) map.get("gid");
        Integer uid = (Integer) map.get("uid");
        Integer gnumber = (Integer) map.get("gnumber");
        //调用服务生成订单
        ordersService.insertOrders(gid,uid,gnumber);
        //redis中移除排队的消息
        redisTemplate.opsForZSet().remove("paidui_"+gid,uid+"");
        //手动确认rebbitmq信息
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
