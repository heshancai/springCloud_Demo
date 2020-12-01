package com.qf.handle;

import com.alibaba.fastjson.JSON;
import com.qf.entity.WebSocketMsgEntity;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * 消息出站的处理器
 */
@Component
@ChannelHandler.Sharable//此消息出战处理器共享
public class WebSocketOutMsgHandler extends ChannelOutboundHandlerAdapter {

    /**
     * 消息出站的处理方法
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //如果返回消息属于文本数据帧内容
        //需要转为json字符串
        if(msg instanceof WebSocketMsgEntity){
            //转为json字符串
            String jsonString = JSON.toJSONString(msg);
            //消息继续出站
            super.write(ctx,new TextWebSocketFrame(jsonString),promise);

            System.out.println("发送消息到客户端提醒秒杀开始"+msg);
        }else {
            super.write(ctx,msg,promise);
        }
    }
}
