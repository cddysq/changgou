package com.changgou.service.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/24 14:24
 * @Description: 品牌通用接口
 */
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 根据分类名称查询品牌列表
     *
     * @param categoryName 分类名称
     * @return 品牌列表
     */
    @Select("SELECT name,image FROM tb_brand WHERE id IN(SELECT brand_id FROM tb_category_brand WHERE category_id IN(SELECT id FROM tb_category WHERE name=#{categoryName}))")
    List<Map<String, Object>> findBrandListByCategoryName(@Param("categoryName") String categoryName);
}