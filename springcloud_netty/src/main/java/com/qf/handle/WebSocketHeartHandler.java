package com.qf.handle;

import com.qf.entity.WebSocketMsgEntity;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * 心跳消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHeartHandler extends SimpleChannelInboundHandler<WebSocketMsgEntity> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketMsgEntity msg) throws Exception {

        if(msg.getType()==2){
            //心跳消息 进行返回给客户端
            ctx.writeAndFlush(msg);
        }else {
            //消息继续往后传递
            ctx.fireChannelRead(msg);
        }
    }
}
