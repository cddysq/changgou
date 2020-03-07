package com.changgou.seckill.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.seckill.config.ConfirmMessageSender;
import com.changgou.seckill.config.RabbitMqConfig;
import com.changgou.seckill.dao.SeckillOrderMapper;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.pojo.SeckillOrder;
import com.changgou.seckill.service.SecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Override
    public boolean add(Long id, String time, String username) {
        //防止用户恶意访问
        String result = this.preventRepeatCommit( username, id );
        if ("fail".equals( result )) {
            return false;
        }
        //防止用户秒杀相同商品
        SeckillOrder order = seckillOrderMapper.getOrderInfoByUserNameAndGoodsId( username, id );
        if (order != null) {
            return false;
        }
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
        //4.发送消息，基于mq进行数据同步
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

    /**
     * 防止用户恶意访问
     *
     * @param username 用户名
     * @param id       商品id
     * @return 是否放行
     */
    private String preventRepeatCommit(String username, Long id) {
        String redisKey = "secKill_user_" + username + "_id_" + id;
        Long count = redisTemplate.opsForValue().increment( redisKey, 1 );
        if (count == 1) {
            //当前用户初次访问，设置五分钟有效期
            redisTemplate.expire( redisKey, 5, TimeUnit.MINUTES );
            return "success";
        } else {
            //非有效期内初次访问，拒绝
            return "fail";
        }
    }
}