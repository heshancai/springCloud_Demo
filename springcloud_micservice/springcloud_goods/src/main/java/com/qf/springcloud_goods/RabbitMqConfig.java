package com.qf.springcloud_goods;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列信息的配置
 */
@Configuration
public class RabbitMqConfig {

    //队列声明
    @Bean
    public Queue getQueue(){
        return new Queue("kill_queue");
    }
    //交换机的声明
    @Bean
    public FanoutExchange getExchange(){
        return new FanoutExchange("kill_exchange");
    }

    //交换机和队列绑定
    @Bean
    public Binding getBinding(Queue getQueue,FanoutExchange getExchange){
        return BindingBuilder.bind(getQueue).to(getExchange);
    }
}
