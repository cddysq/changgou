package com.changgou.user.service;


import com.changgou.user.pojo.Cities;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 10:41
 * @Description: 城市服务
 **/
public interface CitiesService {
    /**
     * 查询所有城市
     *
     * @return 城市集合
     */
    List<Cities> findAll();

    /**
     * 根据id查询城市数据
     *
     * @param id 城市id
     * @return 城市数据
     */
    Cities findById(String id);

    /**
     * 新增城市
     *
     * @param cities 城市数据
     */
    void addCities(Cities cities);

    /**
     * 修改城市信息
     *
     * @param cities 城市数据
     */
    void updateCities(Cities cities);

    /**
     * 根据id删除城市信息
     *
     * @param id 城市id
     */
    void deleteById(String id);

    /**
     * 多条件搜索城市数据
     *
     * @param searchMap 条件集合
     * @return 城市数据
     */
    List<Cities> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Cities> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}