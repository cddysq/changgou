package com.changgou.seckill.service.impl;

import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    /**
     * redis 中秒杀商品库存 key 前缀
     */
    private static final String SEC_KILL_GOODS_STOCK_COUNT_KEY = "sec_kill_goods_stock_count_key";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        List<SeckillGoods> list = redisTemplate.boundHashOps( SEC_KILL_GOODS_KEY + time ).values();
        //更新库存数据来源
        for (SeckillGoods seckillGoods : Objects.requireNonNull( list )) {
            String stockCount = (String) redisTemplate.opsForValue().get( SEC_KILL_GOODS_STOCK_COUNT_KEY + seckillGoods.getId() );
            seckillGoods.setStockCount( Integer.parseInt( Objects.requireNonNull( stockCount ) ) );
        }
        return list;
    }
}