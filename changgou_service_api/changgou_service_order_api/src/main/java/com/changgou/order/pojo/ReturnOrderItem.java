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
 * @Date: 2020/2/26 18:23
 * @Description: 退货订单实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_return_order_item")
public class ReturnOrderItem implements Serializable {
    private static final long serialVersionUID = -7173559280879650774L;
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * SPU_ID
     */
    private String spuId;

    /**
     * SKU_ID
     */
    private String skuId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单明细ID
     */
    private String orderItemId;

    /**
     * 退货订单ID
     */
    private String returnOrderId;

    /**
     * 标题
     */
    private String title;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 总金额
     */
    private Integer money;

    /**
     * 支付金额
     */
    private Integer payMoney;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 重量
     */
    private Integer weight;
}