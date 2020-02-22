package com.changgou.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 22:15
 * @Description: 商品上下架数据库变化监听类
 */
@CanalEventListener
@Slf4j
public class SpuListener {
    private static final String STATE_OF_GOODS = "is_marketable";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ListenPoint(schema = "changgou_goods", table = "tb_spu")
    public void goodsUp(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //获取改变之前的数据
        Map<Object, Object> oldData = new HashMap<>( 0 );
        rowData.getBeforeColumnsList().forEach( c -> oldData.put( c.getName(), c.getValue() ) );

        //获取改变之后的数据
        Map<Object, Object> newData = new HashMap<>( 0 );
        rowData.getAfterColumnsList().forEach( c -> newData.put( c.getName(), c.getValue() ) );

        //获取最新上架的商品,只有满足状态 0 → 1 才能称其为上架
        if ("0".equals( oldData.get( STATE_OF_GOODS ) ) && "1".equals( newData.get( STATE_OF_GOODS ) )) {
            log.info( "监听到新上架商品id：{}", newData.get( "id" ) );
            //将商品的spu id发送到mq
            rabbitTemplate.convertAndSend( RabbitMQConfig.GOODS_UP_EXCHANGE, "", newData.get( "id" ) );
        }

        //获取最新下架的商品,只有满足状态 1 → 0 才能称其为下架
        if ("1".equals( oldData.get( STATE_OF_GOODS ) ) && "0".equals( newData.get( STATE_OF_GOODS ) )) {
            log.info( "监听到新下架商品id：{}", newData.get( "id" ) );
            //将商品的spu id发送到mq
            rabbitTemplate.convertAndSend( RabbitMQConfig.GOODS_DOWN_EXCHANGE, "", newData.get( "id" ) );
        }

        //获取最新被审核通过的商品 status 0 →  1
        if ("0".equals( oldData.get( "status" ) ) && "1".equals( newData.get( "status" ) )) {
            log.info( "商品id:{}已经审核通过", newData.get( "id" ) );
            //将商品的spu id发送到mq
            rabbitTemplate.convertAndSend( RabbitMQConfig.GOODS_UP_EXCHANGE, "", newData.get( "id" ) );
        }
    }
}