package com.qf.handle;

import com.alibaba.fastjson.JSON;
import com.qf.entity.WebSocketMsgEntity;
import com.qf.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * websocket文本数据帧处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 处理器客户端连接的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端连接了！！！");
    }

    /**
     * 处理客户端发送消息的方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("接收到客户端的消息  WebSocketMsgHandler"+msg.text());
        String text = msg.text();
        //将文本数据转为实体类
        WebSocketMsgEntity webSocketMsgEntity = JSON.parseObject(text, WebSocketMsgEntity.class);
        //消息往后传递 其他消息处理器进行处理
        ctx.fireChannelRead(webSocketMsgEntity);
    }

    /**
     * 处理器客户端断开连接的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端断开连接了");

        //下线后移除关系
        Channel channel = ctx.channel();
        //根据uid移除channel
        ChannelUtil.removeChannel(ChannelUtil.getUid(channel));
    }
}
