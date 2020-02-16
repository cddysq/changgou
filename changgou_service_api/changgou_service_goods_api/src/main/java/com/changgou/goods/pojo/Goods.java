package com.changgou.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 22:07
 * @Description: 商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods {
    /**
     * spu
     */
    private Spu spu;

    /**
     * sku集合
     */
    private List<Sku> skuList;
}