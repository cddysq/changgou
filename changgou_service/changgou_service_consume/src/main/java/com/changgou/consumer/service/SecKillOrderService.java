package com.changgou.consumer.service;

import com.changgou.seckill.pojo.SeckillOrder;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 19:38
 * @Description: 秒杀订单服务
 */
public interface SecKillOrderService {
    /**
     * 创建秒杀订单
     *
     * @param seckillOrder 秒杀订单信息
     * @return 1:操作成功 0:操作失败
     */
    int createOrder(SeckillOrder seckillOrder);
}