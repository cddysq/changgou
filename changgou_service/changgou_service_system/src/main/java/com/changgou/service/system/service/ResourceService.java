package com.changgou.service.system.service;


import com.changgou.system.pojo.Resource;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:32
 * @Description: 资源服务
 */
public interface ResourceService {
    /**
     * 查询所有资源
     *
     * @return 资源集合
     */
    List<Resource> findAll();

    /**
     * 根据id查询资源数据
     *
     * @param id 资源id
     * @return 资源数据
     */
    Resource findById(Integer id);

    /**
     * 新增资源
     *
     * @param resource 资源数据
     */
    void addResource(Resource resource);

    /**
     * 更新资源信息
     *
     * @param resource 资源数据
     */
    void updateResource(Resource resource);

    /**
     * 根据id删除资源信息
     *
     * @param id 资源id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索资源数据
     *
     * @param searchMap 条件集合
     * @return 资源数据
     */
    List<Resource> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Resource> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}