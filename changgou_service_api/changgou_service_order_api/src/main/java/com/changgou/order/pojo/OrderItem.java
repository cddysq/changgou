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
 * @Date: 2020/2/26 18:35
 * @Description: 订单实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 2014777992499467293L;
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 1级分类
     */
    private Integer categoryId1;

    /**
     * 2级分类
     */
    private Integer categoryId2;

    /**
     * 3级分类
     */
    private Integer categoryId3;

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
     * 商品名称
     */
    private String name;

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
     * 实付金额
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

    /**
     * 运费
     */
    private Integer postFee;

    /**
     * 是否退货
     */
    private String isReturn;
}