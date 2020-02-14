package com.changgou.service.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:17
 * @Description: 启动类
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.service.system.dao"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run( SystemApplication.class );
    }
}
