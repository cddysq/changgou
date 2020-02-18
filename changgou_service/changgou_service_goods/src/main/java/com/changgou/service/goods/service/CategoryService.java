package com.changgou.service.goods.service;


import com.changgou.goods.pojo.Category;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 18:40
 * @Description: 分类服务
 **/
public interface CategoryService {
    /**
     * 查询所有分类
     *
     * @return 品牌集合
     */
    List<Category> findAll();

    /**
     * 根据id查询分类数据
     *
     * @param id 分类id
     * @return 分类数据
     */
    Category findById(Integer id);

    /**
     * 新增分类
     *
     * @param category 分类数据
     */
    void addCategory(Category category);

    /**
     * 修改分类信息
     *
     * @param category 分类数据
     */
    void updateCategory(Category category);

    /**
     * 根据id删除分类信息
     *
     * @param id 分类id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索分类数据
     *
     * @param searchMap 条件集合
     * @return 分类数据
     */
    List<Category> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Category> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}