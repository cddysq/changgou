package com.changgou.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.changgou.order.dao.TaskHisMapper;
import com.changgou.order.dao.TaskMapper;
import com.changgou.order.pojo.Task;
import com.changgou.order.pojo.TaskHis;
import com.changgou.order.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 16:42
 * @Description: 任务服务实现
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskHisMapper taskHisMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTask(Task task) {
        //1.记录删除时间
        task.setDeleteTime( new Date() );
        Long taskId = task.getId();
        task.setId( null );
        //2.bean 拷贝
        TaskHis taskHis = new TaskHis();
        BeanUtil.copyProperties( task, taskHis );

        //记录历史任务数据
        taskHisMapper.insertSelective( taskHis );

        //删除原有任务数据
        task.setId( taskId );
        taskMapper.deleteByPrimaryKey( task );
        log.info( "订单服务，完成添加历史任务，删除原有任务" );
    }
}
