package com.changgou.business.listener;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 20:21
 * @Description: 广告数据变化监听类
 */
@Component
@Slf4j
public class AdListener {

    @RabbitListener(queues = "ad_update_queue")
    public void receiveMessage(String message) {
        log.info( "接收到的消息为：{}", message );
        //发起远程调用
        String url = "http://192.168.200.128/ad_update?position=" + message;
        String s = HttpUtil.get( url );
        log.info( "返回结果为：{}", s );
    }
}