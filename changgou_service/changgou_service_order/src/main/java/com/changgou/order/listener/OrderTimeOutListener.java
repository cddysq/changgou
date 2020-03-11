package com.changgou.order.listener;

import com.changgou.order.config.RabbitMqConfig;
import com.changgou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 16:35
 * @Description: 订单超时消息监听类
 */
@Component
@Slf4j
public class OrderTimeOutListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_ORDER_TIMEOUT)
    public void receiveCloseOrderMessage(String message) {
        log.info( "接收到关闭订单消息，当前超时订单为：{}", message );
        //调用业务层完成订单关闭
        orderService.closeOrder( message );
    }
}