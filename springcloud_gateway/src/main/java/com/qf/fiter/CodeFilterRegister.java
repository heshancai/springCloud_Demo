package com.qf.fiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 过滤器工厂类
 * 需要过滤的对象进行管理
 */
@Component
public class CodeFilterRegister extends AbstractGatewayFilterFactory {

    //对象进行注入
    @Autowired
    private CodeFilter codeFilter;
    @Override
    public GatewayFilter apply(Object config) {
        return codeFilter;
    }

    //唯一标识
    @Override
    public String name() {
        return "mycode";
    }
}
