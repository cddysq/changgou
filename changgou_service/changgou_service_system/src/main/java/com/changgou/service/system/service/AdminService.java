package com.changgou.service.system.service;


import com.changgou.system.pojo.Admin;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:32
 * @Description: 管理员服务
 */
public interface AdminService {
    /**
     * 查询所有管理员
     *
     * @return 管理员集合
     */
    List<Admin> findAll();

    /**
     * 根据id查询管理员数据
     *
     * @param id 管理员id
     * @return 管理员数据
     */
    Admin findById(Integer id);

    /**
     * 新增管理员
     *
     * @param admin 管理员数据
     */
    void addAdmin(Admin admin);

    /**
     * 更新管理员信息
     *
     * @param admin 管理员数据
     */
    void updateAdmin(Admin admin);

    /**
     * 根据id删除管理员信息
     *
     * @param id 管理员id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索管理员数据
     *
     * @param searchMap 条件集合
     * @return 管理员数据
     */
    List<Admin> findList(Map<String, Object> searchMap);

    /**
     * 分页查询
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示条数
     * @return 分页结果
     */
    Page<Admin> findPage(Integer pageNum, Integer pageSize);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<Admin> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 管理员登录
     *
     * @param admin 管理员信息
     * @return 是否登录成功
     */
    boolean login(Admin admin);
}