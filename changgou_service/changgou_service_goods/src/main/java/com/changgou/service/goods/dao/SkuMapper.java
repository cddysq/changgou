package com.changgou.service.goods.dao;

import com.changgou.goods.pojo.Sku;
import com.changgou.order.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * spu通用接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:59
 **/
public interface SkuMapper extends Mapper<Sku> {
    /**
     * 扣减库存，增加销量
     *
     * @param orderItem 订单项信息
     * @return 影响行数
     */
    @Update("update tb_sku set num=num-#{num},sale_num=sale_num+#{num} where id=#{skuId} and num>=#{num}")
    int decrCount(OrderItem orderItem);

    /**
     * 回滚库存，扣减销量
     *
     * @param skuId  商品id
     * @param number 商品数量
     */
    @Update("update tb_sku set num=num+#{num},sale_num=sale_num-#{num} where id=#{skuId} ")
    void resumeStockNumber(@Param("skuId") String skuId, @Param("num") Integer number);
}