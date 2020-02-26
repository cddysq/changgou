package com.changgou.order.feign;

import com.changgou.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 21:27
 * @Description: 购物车对外接口
 */
@FeignClient(name = "order")
public interface CartFeign {
    /**
     * 添加购物车
     *
     * @param skuId  sku id
     * @param number 商品数量
     * @return 提示信息
     */
    @GetMapping("/cart/addCart")
    Result<Object> addCart(@RequestParam("skuId") String skuId, @RequestParam("number") Integer number);

    /**
     * 查询购物车列表数据
     *
     * @return 购物车信息
     */
    @GetMapping("/cart/list")
    Map<String, Object> list();
}