package com.changgou.service.system.service;


import com.changgou.system.pojo.Menu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:32
 * @Description: 菜单服务
 */
public interface MenuService {
    /**
     * 查询所有菜单
     *
     * @return 菜单集合
     */
    List<Menu> findAll();

    /**
     * 根据id查询菜单数据
     *
     * @param id 菜单id
     * @return 菜单数据
     */
    Menu findById(String id);

    /**
     * 新增菜单
     *
     * @param menu 菜单数据
     */
    void addMenu(Menu menu);

    /**
     * 更新菜单信息
     *
     * @param menu 菜单数据
     */
    void updateMenu(Menu menu);

    /**
     * 根据id删除菜单信息
     *
     * @param id 菜单id
     */
    void deleteById(String id);

    /**
     * 多条件搜索菜单数据
     *
     * @param searchMap 条件集合
     * @return 菜单数据
     */
    List<Menu> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Menu> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}