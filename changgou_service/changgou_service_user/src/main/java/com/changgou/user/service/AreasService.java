package com.changgou.user.service;


import com.changgou.user.pojo.Areas;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:41
 * @Description: 地区服务
 **/
public interface AreasService {
    /**
     * 查询所有地区
     *
     * @return 地区集合
     */
    List<Areas> findAll();

    /**
     * 根据id查询地区数据
     *
     * @param id 地区id
     * @return 地区数据
     */
    Areas findById(String id);

    /**
     * 新增地区
     *
     * @param areas 地区数据
     */
    void addAreas(Areas areas);

    /**
     * 修改地区信息
     *
     * @param areas 地区数据
     */
    void updateAreas(Areas areas);

    /**
     * 根据id删除地区信息
     *
     * @param id 地区id
     */
    void deleteById(String id);

    /**
     * 多条件搜索地区数据
     *
     * @param searchMap 条件集合
     * @return 地区数据
     */
    List<Areas> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Areas> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}