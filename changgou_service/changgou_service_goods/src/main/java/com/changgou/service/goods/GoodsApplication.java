package com.changgou.service.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 商品服务启动类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:46
 **/
@SpringBootApplication
@EnableEurekaClient // 声明当前的工程是eureka客户端
@MapperScan(basePackages = {"com.changgou.service.goods.dao"})
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run( GoodsApplication.class, args );
    }
}