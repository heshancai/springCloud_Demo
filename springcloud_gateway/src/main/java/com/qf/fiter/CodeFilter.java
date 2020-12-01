package com.qf.fiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 路由网关做拦截校验的过程
 * GatewayFilter：实现路由网关的过滤的接口
 * Ordered:路由网关过滤器顺序的实现接口
 */
@Component
public class CodeFilter implements GatewayFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 过滤逻辑的实现
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        //获取cookie中的codeken
        ServerHttpRequest request = exchange.getRequest();
        //获取cookie
        HttpCookie codeToken = request.getCookies().getFirst("codeToken");
        //获取用户输入的验证码的参数
        String code = request.getQueryParams().getFirst("code");
        System.out.println("触发了验证码的过滤"+code);
        //进行验证码的校验
        if(codeToken!=null){
            //获取key值
            String token = codeToken.getValue();
            //获取存储到redis中的验证码的值
            String redisCode = redisTemplate.opsForValue().get(token);
            if(redisCode!=null && redisCode.equals(code)){
                //验证通过，进行放行
                return  chain.filter(exchange);
            }
        }

        //验证码未通过
        ServerHttpResponse response = exchange.getResponse();
        //设置响应的状态码
        response.setStatusCode(HttpStatus.SEE_OTHER);

        //设置重定向的位置
        String msg=null;
        try {
            msg=URLEncoder.encode("验证码错误","utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头  重定向的位置
        response.getHeaders().set("Location","/info/error?msg="+msg);
        return response.setComplete();
    }

    /**
     * 返回当前过滤器的优先级 ，值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 100;
    }
}
