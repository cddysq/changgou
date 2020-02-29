package com.changgou.order.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.config.RabbitMqConfig;
import com.changgou.order.pojo.Task;
import com.changgou.order.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 16:35
 * @Description: mq 积分添加成功队列消息监听类
 */
@Component
@Slf4j
public class DelTaskListener {
    @Autowired
    private TaskService taskService;

    @RabbitListener(queues = RabbitMqConfig.CG_BUYING_FINISH_ADD_POINT)
    public void receiveDelTaskMessage(String message) {
        log.info( "订单服务接收到了删除任务操作消息" );
        taskService.delTask( JSON.parseObject( message, Task.class ) );
    }
}