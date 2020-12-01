package com.qf.fiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 防止提前下单的过滤器
 */
@Component
public class KillFilter implements GatewayFilter, Ordered {

    //lua脚本执行判断当前的商品是否在抢购的时间段
    private String lua="local gid=ARGV[1]\n" +
            "local time=redis.call('get','killgoods_now')\n" +
            "local flag=0\n" +
            "if time then\n" +
            "flag=redis.call('sismember','killgoods_'..time,gid)\n" +
            "end\n" +
            "return flag";

    @Autowired
    private StringRedisTemplate redisTemplate;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取参数中的商品的id
        String gid = exchange.getRequest().getQueryParams().getFirst("gid");
        System.out.println("触发了提前抢购的过滤器:"+gid);


        long l = System.currentTimeMillis();
        System.out.println(l);
        //TODO 方式一:执行lua脚本进行判断
        Long result = redisTemplate.execute(new DefaultRedisScript<>(lua, Long.class), null, gid);
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);
        //TODO 方式二:不使用lua脚本
        //拿到当前的场次的时间
//        String timeNow = redisTemplate.opsForValue().get("killgoods_now");
//        System.out.println(timeNow);
//        boolean flag=false;
//
//        //判断当前的场次中是否存在该商品
//        if(timeNow!=null){
//
//            flag = redisTemplate.opsForSet().isMember("killgoods_" + timeNow, gid);
//        }
//
//        if(flag){
//            System.out.println(flag);
//            //可以抢购
//            return  chain.filter(exchange);
//        }

        //得到lau脚本返回结果进行判断
        //返回1表示可以开始抢购
        if(result==1){
            //放行
            return  chain.filter(exchange);
        }
        //商品未开始抢购
        //响应客户端
        String msg="商品还未开始抢购";
        try {
            msg=URLEncoder.encode(msg,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ServerHttpResponse response = exchange.getResponse();
        //设置响应的状态码
        response.setStatusCode(HttpStatus.SEE_OTHER);
        //设置重定向的位置
        response.getHeaders().set("Location","/info/error?msg="+msg);

        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 200;
    }
}
