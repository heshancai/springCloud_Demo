package com.qf.springcloud_goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient//注册中心的客户端的
@MapperScan("com.qf.dao")//管理dao
//开启事务管理
@EnableTransactionManagement
//缓存的自动管理
@EnableCaching
public class SpringcloudGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGoodsApplication.class, args);
    }

}
