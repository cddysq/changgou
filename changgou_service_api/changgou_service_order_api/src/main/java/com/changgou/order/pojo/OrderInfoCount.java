package com.changgou.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/3/10 12:34
 * @Description: 订单统计信息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoCount {
    /**
     * 统计名
     */
    private String name;
    /**
     * 统计数量
     */
    private Integer value;
}