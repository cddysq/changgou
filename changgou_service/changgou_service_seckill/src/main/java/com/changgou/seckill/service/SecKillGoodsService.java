package com.changgou.seckill.service;

import com.changgou.seckill.pojo.SeckillGoods;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 20:51
 * @Description: 秒杀商品服务
 */
public interface SecKillGoodsService {
    /**
     * 更据当前时间段查询秒杀商品
     *
     * @param time 当前时间段
     * @return 商品列表数据
     */
    List<SeckillGoods> list(String time);
}