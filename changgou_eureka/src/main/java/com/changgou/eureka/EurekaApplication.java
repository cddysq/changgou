package com.changgou.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: Haotian
 * @Date: 2020/1/23 22:17
 * @Description: eureka 服务启动类
 */
@SpringBootApplication
@EnableEurekaServer // 声明当前的工程是eureka的注册中心
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run( EurekaApplication.class, args );
    }
}