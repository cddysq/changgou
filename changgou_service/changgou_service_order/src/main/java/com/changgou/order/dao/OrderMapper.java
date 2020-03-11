package com.changgou.order.dao;

import com.changgou.order.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/2/27 21:10
 * @Description: 订单通用接口
 **/
public interface OrderMapper extends Mapper<Order> {
    /**
     * 统计待付款订单数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 当前时间段待付款订单总数
     */
    @Select("SELECT count(*) FROM tb_order where update_time >=#{startTime} and update_time<=#{endTime} and pay_status='1' and order_status <> '4'")
    int waitPayMoneyCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计待发货订单数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 当前时间段待发货订单总数
     */
    @Select("SELECT count(*) FROM tb_order where update_time >=#{startTime} and update_time<=#{endTime} and consign_status='0' and order_status <> '4' and pay_status='1'")
    int waitSendGoodsCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计已发货订单数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 当前时间段已发货订单总数
     */
    @Select("SELECT count(*) FROM tb_order where update_time >=#{startTime} and update_time<=#{endTime} and consign_status='1'")
    int shippedGoodsCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计已完成订单数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 当前时间段已完成订单总数
     */
    @Select("SELECT count(*) FROM tb_order where update_time >=#{startTime} and update_time<=#{endTime} and order_status='1'")
    int completedCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计已关闭订单数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 当前时间段已关闭订单总数
     */
    @Select("SELECT count(*) FROM tb_order where update_time >=#{startTime} and update_time<=#{endTime} and order_status='4'")
    int closeOrderCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}