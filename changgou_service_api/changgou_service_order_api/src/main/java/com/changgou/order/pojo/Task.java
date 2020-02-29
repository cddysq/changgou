package com.changgou.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 14:13
 * @Description: 任务实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_task")
public class Task implements Serializable {
    private static final long serialVersionUID = -5456418944790644986L;
    /**
     * 任务id
     */
    @Id
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * mq 交换机名称
     */
    @Column(name = "mq_exchange")
    private String mqExchange;

    /**
     * mq 路由key
     */
    @Column(name = "mq_routingkey")
    private String mqRoutingkey;

    /**
     * 任务请求的内容
     */
    @Column(name = "request_body")
    private String requestBody;

    /**
     * 任务状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 任务异常信息
     */
    @Column(name = "errormsg")
    private String errormsg;
}