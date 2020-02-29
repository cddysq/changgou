package com.changgou.order.dao;

import com.changgou.order.pojo.Task;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 15:08
 * @Description: 任务通用接口
 */
public interface TaskMapper extends Mapper<Task> {
    /**
     * 查询最新任务(更新时间小于当前时间)
     *
     * @param currentTime 当前时间
     * @return 最新任务
     */
    @Select("select * from tb_task where update_time<#{currentTime}")
    @Results({@Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_time", property = "deleteTime"),
            @Result(column = "task_type", property = "taskType"),
            @Result(column = "mq_exchange", property = "mqExchange"),
            @Result(column = "mq_routingkey", property = "mqRoutingkey"),
            @Result(column = "request_body", property = "requestBody"),
            @Result(column = "status", property = "status"),
            @Result(column = "errormsg", property = "errormsg")})
    List<Task> findTaskLessThanCurrentTime(Date currentTime);
}