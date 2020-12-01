package com.qf.springcloud_orders;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.dao")
@EnableFeignClients(basePackages = "com.qf.feign")
public class SpringcloudOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudOrdersApplication.class, args);
    }

}
