package com.changgou.order.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.order.config.TokenDecode;
import com.changgou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 19:53
 * @Description: 购物车接口
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 添加购物车
     *
     * @param skuId  sku id
     * @param number 商品数量
     * @return 提示信息
     */
    @GetMapping("/addCart")
    public Result<Object> addCart(@RequestParam("skuId") String skuId, @RequestParam("number") Integer number) {

        //动态获取当前人信息
        String username = tokenDecode.getUserInfo().get( "username" );
        cartService.addCart( skuId, number, username );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "加入购物车成功" ).build();
    }

    /**
     * 查询购物车列表数据
     *
     * @return 购物车信息
     */
    @GetMapping("/list")
    public Map<String, Object> list() {
        String username = tokenDecode.getUserInfo().get( "username" );
        return cartService.list( username );
    }
}