package com.changgou.order.service.impl;

import cn.hutool.core.map.MapUtil;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.feign.SpuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 19:04
 * @Description: 购物车服务实现
 */
@Service
public class CartServiceImpl implements CartService {
    private static final String CART = "cart_";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;

    @Override
    public void addCart(String skuId, Integer number, String username) {
        //1.查询redis中相对应的商品信息
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps( CART + username ).get( skuId );
        if (orderItem != null) {
            //2.如果当前商品在redis中的存在,则更新商品的数量与价钱
            orderItem.setNum( orderItem.getNum() + number );
            orderItem.setMoney( orderItem.getNum() * orderItem.getPrice() );
            orderItem.setPayMoney( orderItem.getNum() * orderItem.getPrice() );
        } else {
            //3.如果当前商品在redis中不存在,将商品添加到redis中
            Sku sku = skuFeign.findById( skuId ).getData();
            Spu spu = spuFeign.findSpuById( sku.getSpuId() ).getData();
            //封装orderItem
            orderItem = this.sku2OrderItem( sku, spu, number );
        }
        //更新数据
        redisTemplate.boundHashOps( CART + username ).put( skuId, orderItem );

    }

    @Override
    public Map<String, Object> list(String username) {
        List<OrderItem> orderItemList = redisTemplate.boundHashOps( CART + username ).values();
        //商品总数量
        Integer totalNum = 0;
        //商品总价格
        Integer totalMoney = 0;
        for (OrderItem orderItem : Objects.requireNonNull( orderItemList )) {
            totalNum += orderItem.getNum();
            totalMoney += orderItem.getMoney();
        }
        return MapUtil.<String, Object>builder()
                .put( "orderItemList", orderItemList )
                .put( "totalNum", totalNum )
                .put( "totalMoney", totalMoney ).build();
    }

    /**
     * 封装购物车商品数据
     *
     * @param sku    sku
     * @param spu    spu
     * @param number 数量
     * @return 购物车商品数据
     */
    private OrderItem sku2OrderItem(Sku sku, Spu spu, Integer number) {
        return OrderItem.builder()
                .spuId( sku.getSpuId() )
                .skuId( sku.getId() )
                .name( sku.getName() )
                .price( sku.getPrice() )
                .num( number )
                .money( sku.getPrice() * number )
                .payMoney( sku.getPrice() * number )
                .image( sku.getImage() )
                .weight( sku.getWeight() * number )
                .categoryId1( spu.getCategory1Id() )
                .categoryId2( spu.getCategory2Id() )
                .categoryId3( spu.getCategory3Id() ).build();
    }
}