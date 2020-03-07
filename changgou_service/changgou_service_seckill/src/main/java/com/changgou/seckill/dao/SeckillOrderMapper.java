package com.changgou.seckill.dao;

import com.changgou.seckill.pojo.SeckillOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 16:43
 * @Description: 秒杀订单通用接口
 **/
public interface SeckillOrderMapper extends Mapper<SeckillOrder> {
    /**
     * 查询秒杀订单
     *
     * @param username 用户名
     * @param id       商品id
     * @return 秒杀订单
     */
    @Select("select * from tb_seckill_order where user_id=#{username} and seckill_id=#{id}")
    SeckillOrder getOrderInfoByUserNameAndGoodsId(@Param("username") String username, @Param("id") Long id);
}