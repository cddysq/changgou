package com.changgou.service.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 品牌服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:02
 **/
public interface BrandService {
    /**
     * 查询所有品牌
     *
     * @return 品牌集合
     */
    List<Brand> findAll();

    /**
     * 根据id查询品牌数据
     *
     * @param id 品牌id
     * @return 品牌数据
     */
    Brand findById(Integer id);

    /**
     * 添加品牌
     *
     * @param brand 品牌数据
     */
    void addBrand(Brand brand);

    /**
     * 更新品牌信息
     *
     * @param brand 品牌数据
     */
    void updateBrand(Brand brand);

    /**
     * 根据id删除品牌信息
     *
     * @param id 品牌id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索品牌数据
     *
     * @param searchMap 条件集合
     * @return 品牌数据
     */
    List<Brand> findList(Map<String, Object> searchMap);

    /**
     * 分页查询
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示条数
     * @return 分页结果
     */
    Page<Brand> findPage(Integer pageNum, Integer pageSize);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Brand> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 根据分类名称查询品牌列表
     *
     * @param categoryName 分类名称
     * @return 品牌列表
     */
    List<Map<String, Object>> findBrandListByCategoryName(String categoryName);
}