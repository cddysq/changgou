package com.changgou.service.goods.dao;

import com.changgou.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 规格通用接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:59
 **/
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