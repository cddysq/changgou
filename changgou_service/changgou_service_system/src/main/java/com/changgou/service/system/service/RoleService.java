package com.changgou.service.system.service;


import com.changgou.system.pojo.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:32
 * @Description: 角色服务
 */
public interface RoleService {
    /**
     * 查询所有角色
     *
     * @return 角色集合
     */
    List<Role> findAll();

    /**
     * 根据id查询角色数据
     *
     * @param id 角色id
     * @return 角色数据
     */
    Role findById(Integer id);

    /**
     * 新增角色
     *
     * @param role 角色数据
     */
    void addRole(Role role);

    /**
     * 更新角色信息
     *
     * @param role 角色数据
     */
    void updateRole(Role role);

    /**
     * 根据id删除角色信息
     *
     * @param id 角色id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索角色数据
     *
     * @param searchMap 条件集合
     * @return 角色数据
     */
    List<Role> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Role> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}