package com.qf.springcloud_netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class SpringcloudNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudNettyApplication.class, args);
    }

}
