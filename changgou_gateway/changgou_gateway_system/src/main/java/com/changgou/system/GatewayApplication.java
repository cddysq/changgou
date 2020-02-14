package com.changgou.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 14:12
 * @Description: 网关启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run( GatewayApplication.class );
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just( Objects.requireNonNull( exchange.getRequest().getRemoteAddress() ).getHostName() );
    }
}