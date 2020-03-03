package com.changgou.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 21:33
 * @Description: mq 配置类
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 自动收货消息队列
     */
    public static final String ORDER_TACK = "order_tack";

    /**
     * 声明队列
     */
    @Bean
    public Queue queue() {
        return new Queue( ORDER_TACK );
    }
}