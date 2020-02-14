package com.changgou.service.system.service;


import com.changgou.system.pojo.LoginLog;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:32
 * @Description: 登录日志服务
 */
public interface LoginLogService {
    /**
     * 查询所有登录日志
     *
     * @return 登录日志集合
     */
    List<LoginLog> findAll();

    /**
     * 根据id查询登录日志数据
     *
     * @param id 登录日志id
     * @return 登录日志数据
     */
    LoginLog findById(Integer id);

    /**
     * 新增登录日志
     *
     * @param loginLog 登录日志数据
     */
    void addLoginLog(LoginLog loginLog);

    /**
     * 更新登录日志信息
     *
     * @param loginLog 登录日志数据
     */
    void updateLoginLog(LoginLog loginLog);

    /**
     * 根据id删除登录日志信息
     *
     * @param id 登录日志id
     */
    void deleteById(Integer id);

    /**
     * 多条件搜索登录日志数据
     *
     * @param searchMap 条件集合
     * @return 登录日志数据
     */
    List<LoginLog> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<LoginLog> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);
}