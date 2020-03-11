package com.changgou.order.feign;

import com.changgou.common.pojo.Result;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderInfoCount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/27 22:16
 * @Description: 订单服务对外接口
 */
@FeignClient(name = "order")
public interface OrderFeign {
    /**
     * 新增订单数据
     *
     * @param order 新订单数据
     * @return 新增结果
     */
    @PostMapping("/order")
    Result<Object> addOrder(@RequestBody Order order);

    /**
     * 根据ID查询订单数据
     *
     * @param id 订单 id
     * @return 订单信息
     */
    @GetMapping("/order/{id}")
    Result<Order> findById(@PathVariable("id") String id);

    /**
     * 查询订单统计信息
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 统计信息集合
     */
    @GetMapping("/order/orderInfoData")
    List<OrderInfoCount> getOrderInfoData(@RequestParam(required = false) String startTime,
                                                  @RequestParam(required = false) String endTime);
}