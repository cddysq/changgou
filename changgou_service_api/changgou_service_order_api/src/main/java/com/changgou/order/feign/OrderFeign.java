package com.changgou.order.feign;

import com.changgou.common.pojo.Result;
import com.changgou.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}