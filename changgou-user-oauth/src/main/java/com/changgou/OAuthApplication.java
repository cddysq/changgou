package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 20:06
 * @Description: 启动类
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.changgou.auth.dao")
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run( OAuthApplication.class, args );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}