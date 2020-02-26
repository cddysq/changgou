package com.changgou.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 18:33
 * @Description: 订单日志实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_order_log")
public class OrderLog implements Serializable {
    private static final long serialVersionUID = -4432293505284983308L;
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 操作员
     */
    private String operater;

    /**
     * 操作时间
     */
    private java.util.Date operateTime;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 付款状态
     */
    private String payStatus;

    /**
     * 发货状态
     */
    private String consignStatus;

    /**
     * 备注
     */
    private String remarks;
}