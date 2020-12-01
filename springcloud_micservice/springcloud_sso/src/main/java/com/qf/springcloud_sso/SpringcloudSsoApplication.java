package com.qf.springcloud_sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.dao")
public class SpringcloudSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudSsoApplication.class, args);
    }

}
