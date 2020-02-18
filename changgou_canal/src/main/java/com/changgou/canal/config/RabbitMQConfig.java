package com.changgou.canal.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 19:51
 * @Description: MQ 配置类
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 定义队列名称
     */
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";

    /**
     * 声明队列
     */
    @Bean
    public Queue queue() {
        //广告队列
        return new Queue( AD_UPDATE_QUEUE );
    }
}