package com.changgou.pay.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Haotian
 * @Date: 2020/3/2 18:20
 * @Description: mq 配置类
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 订单支付成功消息队列
     */
    public static final String ORDER_PAY = "order_pay";

    /**
     * 声明队列
     */
    @Bean
    public Queue queue() {
        return new Queue( ORDER_PAY );
    }
}