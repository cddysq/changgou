package com.changgou.seckill.service.impl;

import com.changgou.common.util.DateUtils;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 20:53
 * @Description: 秒杀服务实现
 */
@Service
public class SecKillGoodsServiceImpl implements SecKillGoodsService {
    /**
     * redis 中秒杀商品 key 前缀
     */
    private static final String SEC_KILL_GOODS_KEY = "sec_kill_goods_key";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        return redisTemplate.boundHashOps( SEC_KILL_GOODS_KEY + time ).values();
    }
}