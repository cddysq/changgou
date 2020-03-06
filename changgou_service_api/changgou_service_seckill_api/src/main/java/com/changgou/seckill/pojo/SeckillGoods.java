package com.changgou.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 16:42
 * @Description: 秒杀商品信息实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_seckill_goods")
public class SeckillGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * spu ID
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * sku ID
     */
    @Column(name = "item_id")
    private Long itemId;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 商品图片
     */
    @Column(name = "small_pic")
    private String smallPic;

    /**
     * 原价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 秒杀价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 商家ID
     */
    @Column(name = "seller_id")
    private String sellerId;

    /**
     * 添加日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核日期
     */
    @Column(name = "check_time")
    private Date checkTime;

    /**
     * 审核状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 秒杀商品数
     */
    @Column(name = "num")
    private Integer num;

    /**
     * 剩余库存数
     */
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 描述
     */
    @Column(name = "introduction")
    private String introduction;
}