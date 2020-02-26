package com.changgou.web.order.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.order.feign.CartFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 21:35
 * @Description: 订单渲染数据接口
 */
@Controller
@RequestMapping("/wcart")
public class CartController {
    @Autowired
    private CartFeign cartFeign;

    /**
     * 查询购物车信息
     *
     * @param model 封装购物车数据的模型
     * @return 购物车列表页面
     */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute( "items", cartFeign.list() );
        return "cart";
    }

    /**
     * 添加商品进购物车
     *
     * @param id     商品id
     * @param number 商品数量
     * @return 提示信息
     */
    @GetMapping("/add")
    @ResponseBody
    public Result<Map<String, Object>> add(String id, Integer number) {
        cartFeign.addCart( id, number );
        return Result.<Map<String, Object>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "添加购物车成功" )
                .data( cartFeign.list() ).build();

    }
}