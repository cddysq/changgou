package com.changgou.page.listener;

import com.changgou.page.config.RabbitMQConfig;
import com.changgou.page.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 19:15
 * @Description: 消息监听
 */
@Component
@Slf4j
public class PageListener {
    @Autowired
    private PageService pageService;

    @RabbitListener(queues = RabbitMQConfig.PAGE_CREATE_QUEUE)
    public void receiveMessage(String spuId) {
        log.info( "获取到静态化页面的商品id为：" + spuId );
        //调用业务实现
        pageService.generateHtml( spuId );
    }
}