package com.changgou.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 20:17
 * @Description: 启动类
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.business.dao"})
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run( BusinessApplication.class );
    }
}