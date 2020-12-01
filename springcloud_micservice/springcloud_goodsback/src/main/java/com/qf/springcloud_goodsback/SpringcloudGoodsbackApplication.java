package com.qf.springcloud_goodsback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication(scanBasePackages = "com.qf")
//注册中心的客户端
@EnableEurekaClient
//也可以扫描到springcloud_feign
@EnableFeignClients(basePackages = "com.qf.feign")
public class SpringcloudGoodsbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGoodsbackApplication.class, args);
    }

}
