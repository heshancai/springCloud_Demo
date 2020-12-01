package com.qf.springcloud_resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringcloudResourcesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudResourcesApplication.class, args);
    }

}
