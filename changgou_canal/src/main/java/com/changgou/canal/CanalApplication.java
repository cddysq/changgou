package com.changgou.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 19:33
 * @Description: 启动类
 */
@SpringBootApplication
@EnableCanalClient //声明当前的服务是canal的客户端
public class CanalApplication {
    public static void main(String[] args) {
        SpringApplication.run( CanalApplication.class, args );
    }
}