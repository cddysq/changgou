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
 * @Date: 2020/2/26 18:30
 * @Description: 优惠活动实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_preferential")
public class Preferential implements Serializable {
    private static final long serialVersionUID = 4259504964048616130L;
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 消费金额
     */
    private Integer buyMoney;

    /**
     * 优惠金额
     */
    private Integer preMoney;

    /**
     * 品类ID
     */
    private Integer categoryId;

    /**
     * 活动开始日期
     */
    private java.util.Date startTime;

    /**
     * 活动截至日期
     */
    private java.util.Date endTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 类型1不翻倍 2翻倍
     */
    private String type;
}