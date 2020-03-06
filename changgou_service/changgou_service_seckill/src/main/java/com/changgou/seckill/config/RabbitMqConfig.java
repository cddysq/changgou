package com.changgou.seckill.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 17:55
 * @Description: mq 配置类
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 秒杀消息队列
     */
    public static final String SEC_KILL_ORDER_QUEUE = "sec_kill_order_queue";

    /**
     * 声明持久化队列
     */
    @Bean
    public Queue queue() {
        return new Queue( SEC_KILL_ORDER_QUEUE, true );
    }
}