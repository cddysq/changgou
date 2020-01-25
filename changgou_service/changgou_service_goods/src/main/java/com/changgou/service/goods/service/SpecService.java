package com.changgou.service.goods.service;


import com.changgou.goods.pojo.Spec;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/25 17:28
 * @Description: 规格服务
 */
public interface SpecService {
    /**
     * 查询所有规格
     *
     * @return 品牌集合
     */
    List<Spec> findAll();

    /**
     * 根据id查询规格数据
     *
     * @param id 规格id
     * @return 规格数据
     */
    Spec findById(Integer id);

    /**
     * 新增规格
     *
     * @param spec 规格数据
     */
    void addSpec(Spec spec);

    /**
     * 修改规格信息
     *
     * @param spec 规格数据
     */
    void updateSpec(Spec spec);

    /**
     * 根据id删除规格信息
     *
     * @param id 规格id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索规格数据
     *
     * @param searchMap 条件集合
     * @return 规格数据
     */
    List<Spec> findList(Map<String, Object> searchMap);

    /**
     * 分页查询
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示条数
     * @return 分页结果
     */
    Page<Spec> findPage(Integer pageNum, Integer pageSize);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Spec> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 根据商品分类名称查询规格列表
     *
     * @param categoryName 分类名称
     * @return 商品规格
     */
    List<Map<String, Object>> findSpecListByCategoryName(String categoryName);
}