package com.changgou.web.order.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.changgou.common.pojo.Result;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.pay.feign.PayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 22:02
 * @Description: 支付访问接口
 */
@Controller
@RequestMapping("/wxpay")
public class PayController {
    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private PayFeign payFeign;

    /**
     * 跳转微信扫描支付界面
     *
     * @param orderId 订单id
     * @param model   数据模型
     * @return 扫描界面
     */
    @GetMapping
    public String wxPay(String orderId, Model model) {
        //1.根据订单id订单，如果订单不存在，跳转错误页面
        Order order = orderFeign.findById( orderId ).getData();
        if (ObjectUtil.isEmpty( order )) {
            return "fail";
        }
        //2.根据订单的支付状态进行判断，如果不是未支付的订单，跳转错误页面
        String payStatus = order.getPayStatus();
        if (!"0".equals( payStatus )) {
            return "fail";
        }
        //3.基于payFeign调用支付接口，获取返回结果
        Result<Object> payResult = payFeign.nativePay( orderId, order.getPayMoney() );
        if (ObjectUtil.isEmpty( payResult.getData() )) {
            return "fail";
        }
        //4.封装结果数据
        Map<String, Object> payMap = Convert.toMap( String.class, Object.class, payResult.getData() );
        payMap.put( "orderId", orderId );
        payMap.put( "payMoney", order.getPayMoney() );
        model.addAllAttributes( payMap );
        return "wxpay";
    }

    /**
     * 跳转支付成功页面
     *
     * @param payMoney 支付金额
     * @return 付款成功页面
     */
    @RequestMapping("/toPaySuccess")
    public String toPaySuccess(Integer payMoney, Model model) {
        model.addAttribute( "payMoney", payMoney );
        return "paysuccess";
    }
}