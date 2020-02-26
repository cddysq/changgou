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
* @Date: 2020/2/26 18:40
* @Description: 配置类
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_order_config")
public class OrderConfig implements Serializable {
    private static final long serialVersionUID = 479140395715405283L;
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 正常订单超时时间（分）
     */
    private Integer orderTimeout;


    /**
     * 秒杀订单超时时间（分）
     */
    private Integer seckillTimeout;

    /**
     * 自动收货（天）
     */
    private Integer takeTimeout;

    /**
     * 售后期限
     */
    private Integer serviceTimeout;

    /**
     * 自动五星好评
     */
    private Integer commentTimeout;
}