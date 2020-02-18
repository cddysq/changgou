package com.changgou.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.canal.config.RabbitMQConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 19:35
 * @Description: 广告数据变化监听类
 */
@CanalEventListener //声明当前的类是canal的监听类
@Slf4j
public class BusinessListener {
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
        //TODO: 2020/2/18 20:31 广告位发生改变，删除原有缓存数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if ("position".equals( column.getName() )) {
                log.info( "发送最新的数据到MQ：{}", column.getValue() );
                //发送消息
                rabbitTemplate.convertAndSend( "", RabbitMQConfig.AD_UPDATE_QUEUE, column.getValue() );
            }
        }

    }
}
