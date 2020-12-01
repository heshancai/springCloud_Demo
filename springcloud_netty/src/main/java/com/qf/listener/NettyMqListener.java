package com.qf.listener;

import com.qf.entity.WebSocketMsgEntity;
import com.qf.util.ChannelUtil;
import io.netty.channel.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 秒送信息推送
 */
@Component
public class NettyMqListener {


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "netty_queue_${server.port}",durable = "true"),
                    exchange = @Exchange(name = "netty_exchange",type = "fanout",durable ="true")

            )
    )
    public void msgHandle(WebSocketMsgEntity webSocketMsgEntity){

        //拿到用户的id
        Integer toId = webSocketMsgEntity.getToId();
        //根据用户id拿到channle对象
        Channel channle = ChannelUtil.getChannle(toId);

        //判断是否为空
        if(channle!=null){
            //用户在线
            channle.writeAndFlush(webSocketMsgEntity);
        }
        System.out.println("有一个消息发给id为:"+toId+"的用户");
    }



}
