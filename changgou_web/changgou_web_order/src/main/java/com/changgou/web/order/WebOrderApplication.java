package com.changgou.web.order;

import com.changgou.common.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 21:21
 * @Description: 启动类
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.order.feign","com.changgou.user.feign"})
public class WebOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run( WebOrderApplication.class, args );
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

}