package com.changgou.seckill.service;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 15:26
 * @Description: 秒杀下单服务
 */
public interface SecKillOrderService {
    /**
     * 秒杀下单
     *
     * @param id       商品id
     * @param time     秒杀时间段
     * @param username 用户名
     * @return 是否秒杀成功
     */
    boolean add(Long id, String time, String username);
}