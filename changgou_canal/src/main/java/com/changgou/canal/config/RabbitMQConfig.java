package com.changgou.canal.config;

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
    public static final String GOODS_DOWN_EXCHANGE = "goods_down_exchange";

    /**
     * 定义队列名称
     */
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";
    public static final String SEARCH_ADD_QUEUE = "search_add_queue";
    public static final String SEARCH_DEL_QUEUE = "search_del_queue";
    public static final String PAGE_CREATE_QUEUE = "page_create_queue";

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
        //商品上架队列
        return new Queue( SEARCH_ADD_QUEUE );
    }

    @Bean(SEARCH_DEL_QUEUE)
    public Queue search_del_queue() {
        //商品下架队列
        return new Queue( SEARCH_DEL_QUEUE );
    }

    @Bean(PAGE_CREATE_QUEUE)
    public Queue page_create_queue() {
        //静态页生成队列
        return new Queue( PAGE_CREATE_QUEUE );
    }

    /**
     * 声明交换机
     */
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange goods_up_exchange() {
        //商品上架交换机
        return ExchangeBuilder.fanoutExchange( GOODS_UP_EXCHANGE ).durable( true ).build();
    }

    @Bean(GOODS_DOWN_EXCHANGE)
    public Exchange goods_down_exchange() {
        //商品下架交换机
        return ExchangeBuilder.fanoutExchange( GOODS_DOWN_EXCHANGE ).durable( true ).build();
    }

    /**
     * 绑定队列与交换机
     */
    @Bean
    public Binding goods_up_exchange_binding(@Qualifier(SEARCH_ADD_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( "" ).noargs();
    }

    @Bean
    public Binding page_create_queue_binding(@Qualifier(PAGE_CREATE_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( "" ).noargs();
    }

    @Bean
    public Binding goods_down_exchange_binding(@Qualifier(SEARCH_DEL_QUEUE) Queue queue, @Qualifier(GOODS_DOWN_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( "" ).noargs();
    }

}