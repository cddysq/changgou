package com.changgou.order.service;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 19:02
 * @Description: 购物车服务
 */
public interface CartService {
    /**
     * 添加购物车
     *
     * @param skuId    商品id
     * @param number   商品数量
     * @param username 所属用户名
     */
    void addCart(String skuId, Integer number, String username);

    /**
     * 查询购物车列表数据
     *
     * @param username 用户名
     * @return 购物车信息
     */

    Map<String, Object> list(String username);
}
