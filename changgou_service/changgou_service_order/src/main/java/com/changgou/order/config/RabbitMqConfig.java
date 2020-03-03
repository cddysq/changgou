package com.changgou.order.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 14:31
 * @Description: mq 积分任务配置类
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 添加积分任务交换机
     */
    public static final String EX_BUYING_ADD_POINT_USER = "ex_buying_add_point_user";

    /**
     * 添加积分消息队列
     */
    public static final String CG_BUYING_ADD_POINT = "cg_buying_add_point";

    /**
     * 完成添加积分消息队列
     */
    public static final String CG_BUYING_FINISH_ADD_POINT = "cg_buying_finish_add_point";

    /**
     * 添加积分路由key
     */
    public static final String CG_BUYING_ADD_POINT_KEY = "add_point";

    /**
     * 完成添加积分路由key
     */
    public static final String CG_BUYING_FINISH_ADD_POINT_KEY = "finish_add_point";

    /**
     * 订单支付成功消息队列
     */
    public static final String ORDER_PAY = "order_pay";

    /**
     * 订单延迟消息通知队列
     */
    public static final String QUEUE_ORDER_CREATE = "queue.ordercreate";

    /**
     * 订单超时消息队列
     */
    public static final String QUEUE_ORDER_TIMEOUT = "queue.ordertimeout";

    /**
     * 自动收货消息队列
     */
    public static final String ORDER_TACK = "order_tack";

    /**
     * 声明交换机
     */
    @Bean(EX_BUYING_ADD_POINT_USER)
    public Exchange ex_buying_add_point_user() {
        return ExchangeBuilder.directExchange( EX_BUYING_ADD_POINT_USER ).durable( true ).build();
    }

    /**
     * 声明队列
     */
    @Bean(CG_BUYING_ADD_POINT)
    public Queue cg_buying_add_point() {
        return new Queue( CG_BUYING_ADD_POINT );
    }

    @Bean(CG_BUYING_FINISH_ADD_POINT)
    public Queue cg_buying_finish_add_point() {
        return new Queue( CG_BUYING_FINISH_ADD_POINT );
    }

    @Bean
    public Queue queue() {
        return new Queue( ORDER_PAY );
    }

    @Bean
    public Queue order_tack() {
        return new Queue( ORDER_TACK );
    }

    /**
     * 队列绑定交换机
     */
    @Bean
    public Binding binding_cg_buying_add_point(@Qualifier(CG_BUYING_ADD_POINT) Queue queue, @Qualifier(EX_BUYING_ADD_POINT_USER) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( CG_BUYING_ADD_POINT_KEY ).noargs();
    }

    @Bean
    Binding binding_cg_buying_finish_add_point(@Qualifier(CG_BUYING_FINISH_ADD_POINT) Queue queue, @Qualifier(EX_BUYING_ADD_POINT_USER) Exchange exchange) {
        return BindingBuilder.bind( queue ).to( exchange ).with( CG_BUYING_FINISH_ADD_POINT_KEY ).noargs();
    }
}