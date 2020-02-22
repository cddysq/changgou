package com.changgou.goods.feign;

import com.changgou.common.pojo.Result;
import com.changgou.goods.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 18:31
 * @Description: 商品feign接口
 **/
@FeignClient(name = "goods")
public interface SpuFeign {
    /**
     * 查询spu信息
     *
     * @param id spu id
     * @return id 对应数据
     */
    @GetMapping("/spu/findSpuById/{id}")
    Result<Spu> findSpuById(@PathVariable("id") String id);
}