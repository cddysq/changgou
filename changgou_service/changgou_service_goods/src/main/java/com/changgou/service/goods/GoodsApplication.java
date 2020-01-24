package com.changgou.service.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: Haotian
 * @Date: 2020/1/24 10:04
 * @Description: 商品服务启动类
 **/
@SpringBootApplication
@EnableEurekaClient //声明当前的工程是eureka客户端
@MapperScan(basePackages = {"com.changgou.service.goods.dao"})
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run( GoodsApplication.class, args );
    }
}