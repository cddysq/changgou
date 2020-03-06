package com.changgou.seckill.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.seckill.config.ConfirmMessageSender;
import com.changgou.seckill.config.RabbitMqConfig;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 15:28
 * @Description: 秒杀下单服务实现
 */
@Service
public class SecKillOrderServiceImpl implements SecKillOrderService {
    /**
     * redis 中秒杀商品 key 前缀
     */
    private static final String SEC_KILL_GOODS_KEY = "sec_kill_goods_key";
    /**
     * redis 中秒杀商品库存 key 前缀
     */
    private static final String SEC_KILL_GOODS_STOCK_COUNT_KEY = "sec_kill_goods_stock_count_key";
    private Snowflake snowflake = IdUtil.createSnowflake( 1, 1 );
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ConfirmMessageSender confirmMessageSender;

    @Override
    public boolean add(Long id, String time, String username) {
        //1.获取redis中的商品信息与库存信息
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps( SEC_KILL_GOODS_KEY + time ).get( id );
        if (ObjectUtil.isEmpty( seckillGoods )) {
            return false;
        }
        String stockCount = (String) redisTemplate.opsForValue().get( SEC_KILL_GOODS_STOCK_COUNT_KEY + id );
        if (StrUtil.isEmpty( stockCount )) {
            return false;
        }
        //2.执行redis中的预扣减库存 decrement:键对应的value减一 increment:键对应的value加一
        Long decrement = redisTemplate.opsForValue().decrement( SEC_KILL_GOODS_STOCK_COUNT_KEY + id );
        if (decrement <= 0) {
            //3.库存<=0，删除商品,清理库存
            redisTemplate.boundHashOps( SEC_KILL_GOODS_KEY + time ).delete( id );
            redisTemplate.delete( SEC_KILL_GOODS_STOCK_COUNT_KEY + id );
        }
        //4.基于mq进行数据同步，进行异步下单扣减mysql中的库存数
        SeckillOrder seckillOrder = SeckillOrder.builder()
                .id( snowflake.nextId() )
                .seckillId( id )
                .money( Objects.requireNonNull( seckillGoods ).getCostPrice() )
                .sellerId( seckillGoods.getSellerId() )
                .createTime( new Date() )
                .status( "0" ).build();
        confirmMessageSender.sendMessage( "", RabbitMqConfig.SEC_KILL_ORDER_QUEUE, JSON.toJSONString( seckillOrder ) );
        return true;
    }
}