package com.changgou.user.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.order.pojo.Task;
import com.changgou.user.config.RabbitMqConfig;
import com.changgou.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 15:46
 * @Description: mq 添加积分队列监听类
 */
@Component
@Slf4j
public class AddPointListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMqConfig.CG_BUYING_ADD_POINT)
    public void receiveAddPointMessage(String message) {
        log.info( "用户服务接收到任务消息" );
        //转换消息
        Task task = JSON.parseObject( message, Task.class );
        if (ObjectUtil.isEmpty( task ) || StrUtil.isEmpty( task.getRequestBody() )) {
            //不存在消息，直接返回
            return;
        }

        //判断 redis 中当前的任务是否存在
        Object value = redisTemplate.boundValueOps( task.getId() ).get();
        if (ObjectUtil.isNotEmpty( value )) {
            return;
        }

        //更新用户积分
        int result = userService.updateUserPoint( task );
        if (result <= 0) {
            return;
        }

        //向mq发送处理信息
        rabbitTemplate.convertAndSend( RabbitMqConfig.EX_BUYING_ADD_POINT_USER, RabbitMqConfig.CG_BUYING_FINISH_ADD_POINT_KEY, JSON.toJSONString( task ) );
        log.info( "用户服务向完成积分添加队列发送了一条消息" );
    }
}