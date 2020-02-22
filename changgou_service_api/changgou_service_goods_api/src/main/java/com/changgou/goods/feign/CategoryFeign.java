package com.changgou.goods.feign;

import com.changgou.common.pojo.Result;
import com.changgou.goods.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Haotian
 * @Date: 2020/2/22 18:30
 * @Description: 分类feign接口
 **/
@FeignClient(name = "goods")
public interface CategoryFeign {
    /**
     * 查询分类信息
     *
     * @param id 分类id
     * @return 指定id分类信息
     */
    @GetMapping("/category/{id}")
    Result<Category> findById(@PathVariable("id") Integer id);
}