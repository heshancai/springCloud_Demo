package com.qf.handle;

import com.qf.entity.WebSocketMsgEntity;
import com.qf.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * 文本 数据帧处理器后
 * 对象处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketInitHandler extends SimpleChannelInboundHandler<WebSocketMsgEntity> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketMsgEntity msg) throws Exception {
        //新的用户连接服务器
        if(msg.getType()==1){
            //拿到客户端的用户id
            Integer uid = (Integer) msg.getData();
            //拿到当前用户的channel对象
            Channel channel = ctx.channel();
            //添加到map集合进行管理
            ChannelUtil.add(uid,channel);
            System.out.println(msg);
        }else {
            //不是新的客户端的连接
            //消息往后传递 其他的消息处理器进行处理
            ctx.fireChannelRead(msg);
        }
    }
}
