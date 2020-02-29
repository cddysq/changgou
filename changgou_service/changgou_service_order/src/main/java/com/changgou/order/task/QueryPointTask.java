package com.changgou.order.task;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.order.config.RabbitMqConfig;
import com.changgou.order.dao.TaskMapper;
import com.changgou.order.pojo.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 15:21
 * @Description: 定时扫描任务表数据
 */
@Component
@Slf4j
public class QueryPointTask {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/2 * * * * ?")
    public void queryTask() {
        //1.获取最新任务数据
        List<Task> taskList = taskMapper.findTaskLessThanCurrentTime( new Date() );
        if (ObjectUtil.isNotEmpty( taskList )) {
            for (Task task : taskList) {
                //2.将任务数据发送到消息队列
                rabbitTemplate.convertAndSend( RabbitMqConfig.EX_BUYING_ADD_POINT_USER, RabbitMqConfig.CG_BUYING_ADD_POINT_KEY, JSON.toJSONString( task ) );
                log.info( "订单服务向添加积分队列发送了一条消息" );
            }
        }
    }
}