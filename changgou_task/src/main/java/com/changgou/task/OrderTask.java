package com.changgou.task;

import com.changgou.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 21:36
 * @Description: 自动确认收货任务
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void autoTask() {
        log.info( "开始发送自动收货消息" );
        rabbitTemplate.convertAndSend( "", RabbitMqConfig.ORDER_TACK, "info" );
    }
}