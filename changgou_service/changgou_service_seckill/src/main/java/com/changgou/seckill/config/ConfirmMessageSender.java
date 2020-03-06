package com.changgou.seckill.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 17:58
 * @Description: 消息确认机制保证消息可靠性
 */
@Component
public class ConfirmMessageSender implements RabbitTemplate.ConfirmCallback {
    /**
     * 消息确认key前缀
     */
    private static final String MESSAGE_CONFIRM_KEY = "message_confirm_key";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public ConfirmMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback( this );
    }

    /**
     * 接受消息返回通知
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String correlationDataId = correlationData.getId();
        if (ack) {
            //发送成功，删除redis中的数据备份
            redisTemplate.delete( Objects.requireNonNull( correlationDataId ) );
            redisTemplate.delete( MESSAGE_CONFIRM_KEY + correlationDataId );
        } else {
            //发送失败，再次发送消息
            Map<String, String> map = redisTemplate.opsForHash().entries( MESSAGE_CONFIRM_KEY + correlationDataId );
            String exchange = map.get( "exchange" );
            String routingKey = map.get( "routingKey" );
            String message = map.get( "message" );
            rabbitTemplate.convertAndSend( exchange, routingKey, message );
        }
    }

    /**
     * 自定义消息发送方法
     *
     * @param exchange   交换机名
     * @param routingKey 路由key
     * @param message    消息内容
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        //设置消息的唯一标识并存入redis中
        CorrelationData correlationData = new CorrelationData( IdUtil.randomUUID() );
        String correlationDataId = correlationData.getId();
        redisTemplate.opsForValue().set( Objects.requireNonNull( correlationDataId ), message );
        //将本次发送消息的相关元数据保存到redis中
        Map<Object, Object> map = MapUtil.builder()
                .put( "exchange", exchange )
                .put( "routingKey", routingKey )
                .put( "message", message ).build();
        redisTemplate.opsForHash().putAll( MESSAGE_CONFIRM_KEY + correlationDataId, map );
        //向消息队列发送消息
        rabbitTemplate.convertAndSend( exchange, routingKey, message, correlationData );
    }
}