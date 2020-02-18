package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}