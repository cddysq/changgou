package com.changgou.user.dao;

import com.changgou.user.pojo.PointLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author: Haotian
 * @Date: 2020/2/29 16:12
 * @Description: 任务日志通用接口
 */
public interface PointLogMapper extends Mapper<PointLog> {
    /**
     * 根据订单id查询任务日志
     *
     * @param orderId 订单id
     * @return 订单id所属日志记录
     */
    @Select("select * from tb_point_log where order_id=#{orderId}")
    PointLog findPointLogByOrderId(@Param("orderId") String orderId);
}