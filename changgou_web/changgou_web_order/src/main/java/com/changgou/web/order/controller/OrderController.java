package com.changgou.web.order.controller;

import com.changgou.common.pojo.Result;
import com.changgou.order.feign.CartFeign;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import com.changgou.order.pojo.OrderItem;
import com.changgou.user.feign.AddressFeign;
import com.changgou.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/27 18:38
 * @Description: 订单渲染数据接口
 */
@Controller
@RequestMapping("/worder")
public class OrderController {
    @Autowired
    private AddressFeign addressFeign;
    @Autowired
    private CartFeign cartFeign;
    @Autowired
    private OrderFeign orderFeign;

    /**
     * 跳转订单页面
     *
     * @return 订单页面
     */
    @RequestMapping("/ready/order")
    public String readyOrder(Model model) {
        //收件人的信息
        List<Address> addressList = addressFeign.list().getData();
        model.addAttribute( "addressList", addressList );

        //购物车的信息
        Map<String, Object> map = cartFeign.list();
        //购物车商品数据
        List<OrderItem> orderItemList = (List<OrderItem>) map.get( "orderItemList" );
        model.addAttribute( "carts", orderItemList );
        //商品总数量
        Integer totalNum = (Integer) map.get( "totalNum" );
        model.addAttribute( "totalNum", totalNum );
        //商品总价钱
        Integer totalMoney = (Integer) map.get( "totalMoney" );
        model.addAttribute( "totalMoney", totalMoney );

        //发送默认收件人信息
        for (Address address : addressList) {
            if ("1".equals( address.getIsDefault() )) {
                //是默认
                model.addAttribute( "defaultAddress", address );
                break;
            }
        }
        return "order";
    }

    /**
     * 新增订单
     *
     * @param order 订单数据
     * @return 新增提示信息
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Object> addOrder(@RequestBody Order order) {
        return orderFeign.addOrder( order );
    }

    /**
     * 跳转支付页面
     *
     * @param orderId 订单id
     * @param model   封装数据模型
     * @return 支付页面
     */
    @GetMapping("/toPayPage")
    public String toPayPage(String orderId, Model model) {
        Order order = orderFeign.findById( orderId ).getData();
        model.addAttribute( "orderId", orderId );
        model.addAttribute( "payMoney", order.getPayMoney() );
        return "pay";
    }
}