package com.changgou.order.listener;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.changgou.order.config.RabbitMqConfig;
import com.changgou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/2 18:29
 * @Description: 订单支付结果消息监听类
 */
@Component
@Slf4j
public class OrderPayListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = RabbitMqConfig.ORDER_PAY)
    public void receiveMessage(String message) {
        log.info( "接收到订单支付成功消息内容：{}", message );
        Map map = JSON.parseObject( message, Map.class );
        String orderId = Convert.toStr( map.get( "orderId" ) );
        String transactionId = Convert.toStr( map.get( "transactionId" ) );

        //修改订单数据库
        orderService.updatePayStatus( orderId, transactionId );
    }
}