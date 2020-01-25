package com.changgou.service.goods.dao;

import com.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/25 17:21
 * @Description: 规格通用接口
 */
public interface SpecMapper extends Mapper<Spec> {
    /**
     * 根据商品分类名称查询规格列表
     *
     * @param categoryName 分类名称
     * @return 商品规格
     */
    @Select("SELECT name,options FROM tb_spec WHERE template_id IN(SELECT id FROM tb_template WHERE name=#{categoryName})")
    List<Map<String, Object>> findSpecListByCategoryName(@Param("categoryName") String categoryName);
}