package com.changgou.logistics;


import com.changgou.common.interceptor.FeignInterceptor;
import com.changgou.logistics.config.TokenDecode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 18:59
 * @Description: 启动类
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.order.feign"})
public class LogisticApplication {
    public static void main(String[] args) {
        SpringApplication.run( LogisticApplication.class );
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }
}