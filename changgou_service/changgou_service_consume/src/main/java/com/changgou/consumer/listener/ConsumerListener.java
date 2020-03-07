package com.changgou.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.consumer.config.RabbitMqConfig;
import com.changgou.consumer.service.SecKillOrderService;
import com.changgou.seckill.pojo.SeckillOrder;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 19:20
 * @Description: 秒杀消息监听类
 */
@Component
public class ConsumerListener {
    @Autowired
    private SecKillOrderService secKillOrderService;

    @RabbitListener(queues = RabbitMqConfig.SEC_KILL_ORDER_QUEUE)
    public void receiveSecKillOrderMessage(Message message, Channel channel) {
        //设置消息抓取总数
        try {
            channel.basicQos( 300 );
        } catch (IOException e) {
            e.printStackTrace();
        }
        //1.转换消息格式
        SeckillOrder seckillOrder = JSON.parseObject( message.getBody(), SeckillOrder.class );
        //2.接收消息，进行异步下单扣减mysql中的库存数
        int result = secKillOrderService.createOrder( seckillOrder );
        if (result > 0) {
            //同步mysql成功，向mq返回成功通知
            try {
                channel.basicAck( message.getMessageProperties().getDeliveryTag(), false );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //同步失败，向mq返回失败通知
            try {
                /*
                 * 第三个参数：false当前消息作为一个死信消息，如果没有绑定死信交换机，消息将会丢弃。true消息会回到原有队列中，默认回到头部。
                 */
                channel.basicNack( message.getMessageProperties().getDeliveryTag(), false, true );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}