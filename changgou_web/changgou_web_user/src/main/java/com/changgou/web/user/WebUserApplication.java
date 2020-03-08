package com.changgou.web.user;

import com.changgou.common.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Haotian
 * @Date: 2020/3/8 13:16
 * @Description: 启动类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.user.feign"})
public class WebUserApplication {
    public static void main(String[] args) {
        SpringApplication.run( WebUserApplication.class, args );
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }
}