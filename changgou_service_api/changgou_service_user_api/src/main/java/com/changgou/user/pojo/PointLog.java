package com.changgou.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 14:28
 * @Description: 积分日志表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_point_log")
public class PointLog implements Serializable {
    private static final long serialVersionUID = -3612556668929323113L;
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户名
     */
    private String userId;

    /**
     * 积分数
     */
    private Integer point;
}