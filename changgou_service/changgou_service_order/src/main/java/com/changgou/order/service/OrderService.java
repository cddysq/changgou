package com.changgou.order.service;


import com.changgou.order.pojo.Order;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 18:40
 * @Description: 订单服务
 **/
public interface OrderService {
    /**
     * 查询所有订单
     *
     * @return 订单集合
     */
    List<Order> findAll();

    /**
     * 根据id查询订单数据
     *
     * @param id 订单id
     * @return 订单数据
     */
    Order findById(String id);

    /**
     * 新增订单
     *
     * @param order 订单数据
     * @return 订单号
     */
    String addOrder(Order order);

    /**
     * 修改订单信息
     *
     * @param order 订单数据
     */
    void updateOrder(Order order);

    /**
     * 根据id删除订单信息
     *
     * @param id 订单id
     */
    void deleteById(String id);

    /**
     * 多条件搜索订单数据
     *
     * @param searchMap 条件集合
     * @return 订单数据
     */
    List<Order> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Order> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 修改订单支付状态，并记录日志
     *
     * @param orderId       订单id
     * @param transactionId 微信支付订单号
     */
    void updatePayStatus(String orderId, String transactionId);

    /**
     * 关闭订单
     *
     * @param orderId 订单号
     */
    void closeOrder(String orderId);

    /**
     * 批量发货
     *
     * @param orderList 订单集合
     * @return 提示信息
     */
    Map<String, Object> batchSend(List<Order> orderList);

    /**
     * 确认收货
     *
     * @param orderId  订单号
     * @param operator 操做人
     */
    void confirmTask(String orderId, String operator);

    /**
     * 自动收货
     */
    void autoTack();
}