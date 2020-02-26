package com.changgou.user.service;


import com.changgou.user.pojo.Provinces;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:41
 * @Description: 省份服务
 **/
public interface ProvincesService {
    /**
     * 查询所有省份
     *
     * @return 省份集合
     */
    List<Provinces> findAll();

    /**
     * 根据id查询省份数据
     *
     * @param id 省份id
     * @return 省份数据
     */
    Provinces findById(String id);

    /**
     * 新增省份
     *
     * @param provinces 省份数据
     */
    void addProvinces(Provinces provinces);

    /**
     * 修改省份信息
     *
     * @param provinces 省份数据
     */
    void updateProvinces(Provinces provinces);

    /**
     * 根据id删除省份信息
     *
     * @param id 省份id
     */
    void deleteById(String id);

    /**
     * 多条件搜索省份数据
     *
     * @param searchMap 条件集合
     * @return 省份数据
     */
    List<Provinces> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Provinces> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}