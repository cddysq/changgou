package com.changgou.search.listener;

import com.changgou.search.config.RabbitMQConfig;
import com.changgou.search.service.EsManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:53
 * @Description: 商品变化监听
 */
@Component
@Slf4j
public class GoodsUpListener {
    @Autowired
    private EsManagerService esManagerService;

    @RabbitListener(queues = RabbitMQConfig.SEARCH_ADD_QUEUE)
    public void receiveMessage(String spuId) {
        log.info( "接收到新上架商品id：{}", spuId );
        //查询skuList,并导入到索引库
        esManagerService.importDataBySpuId( spuId );
    }
}
