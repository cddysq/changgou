package com.changgou.order.listener;

import cn.hutool.core.date.DateUtil;
import com.changgou.order.config.RabbitMqConfig;
import com.changgou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 21:43
 * @Description: 自动收货消息监听类
 */
@Component
@Slf4j
public class OrderTaskListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = RabbitMqConfig.ORDER_TACK)
    public void receiveOrderTaskMessage(String message) {
        log.info( "开始执行自动收货任务，当前时间{}", DateUtil.formatDateTime( new Date() ) );
        //自动收货
        orderService.autoTack();
    }
}
