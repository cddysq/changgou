package com.changgou.search.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
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
     * 定义交换机名称
     */
    public static final String GOODS_UP_EXCHANGE = "goods_up_exchange";

    /**
     * 定义队列名称
     */
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";
    public static final String SEARCH_ADD_QUEUE = "search_add_queue";

    /**
     * 声明队列
     */
    @Bean
    public Queue queue() {
        //广告队列
        return new Queue( AD_UPDATE_QUEUE );
    }

    @Bean(SEARCH_ADD_QUEUE)
    public Queue search_add_queue() {
        //商品队列
        return new Queue( SEARCH_ADD_QUEUE );
    }

    /**
     * 声明交换机
     */
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange goods_up_exchange() {
        //商品交换机
        return ExchangeBuilder.fanoutExchange( GOODS_UP_EXCHANGE ).durable( true ).build();
    }

    /**
     * 绑定队列与交换机
     */
    @Bean
    public Binding goods_up_exchange_binding(@Qualifier(SEARCH_ADD_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( "" ).noargs();
    }

}