package com.qf.springcloud_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
public class SpringcloudFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudFrontApplication.class, args);
    }

}
