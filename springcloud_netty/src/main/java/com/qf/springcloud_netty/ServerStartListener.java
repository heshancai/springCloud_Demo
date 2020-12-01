package com.qf.springcloud_netty;

import com.qf.handle.WebSocketHeartHandler;
import com.qf.handle.WebSocketInitHandler;
import com.qf.handle.WebSocketMsgHandler;
import com.qf.handle.WebSocketOutMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Websocket的netty
 * 服务
 */
@Component
public class ServerStartListener implements CommandLineRunner {


    //创建主从线程池
    NioEventLoopGroup master=new NioEventLoopGroup();
    NioEventLoopGroup slave=new NioEventLoopGroup();

    @Value("${server.port}")
    private int port;
    //注入消息出战处理器
    @Autowired
    private WebSocketOutMsgHandler outMsgHandler;

    //本文数据帧消息处理器
    @Autowired
    private WebSocketMsgHandler socketMsgHandler;

    //对象消息处理器
    @Autowired
    private WebSocketInitHandler initHandler;

    @Autowired
    private WebSocketHeartHandler socketHeartHandler;


    @Override
    public void run(String... args) throws Exception {


        //配置服务器初始化引导文件
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(master,slave)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //配置请求解码和响应编码的处理器
                        pipeline.addLast(new HttpServerCodec());
                        //配置http请求聚合器
                        pipeline.addLast(new HttpObjectAggregator(1024*1024));
                        //添加对websocket请求的处理器
                        pipeline.addLast(new WebSocketServerProtocolHandler("/msg"));
                        //添加消息超时的处理器
                        pipeline.addLast(new ReadTimeoutHandler(5, TimeUnit.MINUTES));

                        //设置出站消息的处理器
                        pipeline.addLast(outMsgHandler);

                        //处理器链添加消息入站处理器
                        //添加websocket文本数据帧处理器
                        pipeline.addLast(socketMsgHandler);
                        //对象消息处理器器
                        pipeline.addLast(initHandler);
                        //心跳消息处理器
                        pipeline.addLast(socketHeartHandler);

                    }
                });
        serverBootstrap.bind(port).sync();
        System.out.println("WebSocket服务器的启动成功，端口为:"+port);
    }
}
