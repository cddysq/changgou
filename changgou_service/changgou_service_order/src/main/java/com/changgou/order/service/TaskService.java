package com.changgou.order.service;

import com.changgou.order.pojo.Task;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 16:40
 * @Description: 任务服务
 */
public interface TaskService {
    /**
     * 删除任务
     *
     * @param task 任务信息
     */
    void delTask(Task task);
}