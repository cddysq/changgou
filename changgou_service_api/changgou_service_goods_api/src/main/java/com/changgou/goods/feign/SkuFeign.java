package com.changgou.goods.feign;

import com.changgou.common.pojo.Result;
import com.changgou.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 23:00
 * @Description: 商品feign接口
 **/
@FeignClient(name = "goods")
public interface SkuFeign {

    /**
     * 查找所有商品
     *
     * @param spuId 商品id
     * @return 商品数据
     */
    @GetMapping("/sku/spu/{spuId}")
    List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);

    /**
     * 根据ID查询Sku数据
     *
     * @param id Sku id
     * @return Sku信息
     */
    @GetMapping("sku/{id}")
    Result<Sku> findById(@PathVariable("id") String id);

    /**
     * 扣减库存，增加销量
     *
     * @param username 当前下单用户名
     * @return 操作提示
     */
    @PostMapping("/sku/decr/count")
    Result<Object> decrCount(@RequestParam("username") String username);

    /**
     * 回滚库存，扣减销量
     *
     * @param skuId  商品id
     * @param number 商品数量
     * @return 操作提示
     */
    @PostMapping("/sku/resumeStockNumber")
    Result<Object> resumeStockNumber(@RequestParam("skuId") String skuId, @RequestParam("num") Integer number);
}