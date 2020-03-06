package com.changgou.consumer.dao;

import com.changgou.seckill.pojo.SeckillGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 16:43
 * @Description: 秒杀商品信息通用接口
 **/
public interface SeckillGoodsMapper extends Mapper<SeckillGoods> {
    /**
     * 更新秒杀商品库存
     *
     * @param id 商品id
     * @return 影响行数
     */
    @Update("update tb_seckill_goods set stock_count=stock_count-1 where id=#{id} and stock_count>=1")
    int updateStockCount(@Param("id") Long id);
}