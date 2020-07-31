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
 * 广告数据变化监听类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/31 15:45
 **/
@CanalEventListener //声明当前的类是canal的监听类
@Slf4j
public class BusinessListener {
    private static final String AD_POSITION = "position";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 更新广告数据
     *
     * @param eventType 当前操作数据库的类型
     * @param rowData   当前操作数据库的数据
     */
    @ListenPoint(schema = "changgou_business", table = "tb_ad")
    public void adUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        log.info( "广告表数据发生改变" );
        // 遍历改变之前的数据
        Map<Object, Object> oldData = new HashMap<>( 0 );
        rowData.getBeforeColumnsList().forEach( c -> oldData.put( c.getName(), c.getValue() ) );

        // 遍历改变之后的数据
        Map<Object, Object> newData = new HashMap<>( 0 );
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            newData.put( column.getName(), column.getValue() );
            if (AD_POSITION.equals( column.getName() )) {
                log.info( "发送最新的数据到MQ：{}", column.getValue() );
                //发送消息
                rabbitTemplate.convertAndSend( "", RabbitMQConfig.AD_UPDATE_QUEUE, column.getValue() );
            }
        }
        // 广告位置发生改变，需额外更新就广告位数据
        if (!newData.get( AD_POSITION ).equals( oldData.get( AD_POSITION ) )) {
            log.info( "广告位发生改变，发送旧广告位数据到MQ：{}", oldData.get( AD_POSITION ) );
            // 发送消息
            rabbitTemplate.convertAndSend( "", RabbitMQConfig.AD_UPDATE_QUEUE, oldData.get( AD_POSITION ) );
        }
    }
}
