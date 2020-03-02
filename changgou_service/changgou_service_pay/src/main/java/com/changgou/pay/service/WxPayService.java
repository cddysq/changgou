package com.changgou.pay.service;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 21:23
 * @Description: 微信支付接口
 */
public interface WxPayService {
    /**
     * 统一微信支付接口
     *
     * @param orderId 订单id
     * @param money   付款金额
     * @return 提示信息
     */
    Map<String, String> nativePay(String orderId, Integer money);

    /**
     * 基于微信接口查询订单
     *
     * @param orderId 订单号
     * @return 提示信息
     */
    Map<String, String> queryOrder(String orderId);
}