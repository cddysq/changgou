package com.changgou.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/31 15:46
 **/
@SpringBootApplication
@EnableCanalClient //声明当前的服务是canal的客户端
public class CanalApplication {
    public static void main(String[] args) {
        SpringApplication.run( CanalApplication.class, args );
    }
}