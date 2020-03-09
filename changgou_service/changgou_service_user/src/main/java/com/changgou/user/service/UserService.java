package com.changgou.user.service;


import com.changgou.order.pojo.Task;
import com.changgou.user.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/18 18:40
 * @Description: 用户服务
 **/
public interface UserService {
    /**
     * 查询所有用户
     *
     * @return 用户集合
     */
    List<User> findAll();

    /**
     * 根据用户名查询用户数据
     *
     * @param username 用户名
     * @return 用户数据
     */
    User findById(String username);

    /**
     * 新增用户
     *
     * @param user 用户数据
     */
    void addUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户数据
     */
    void updateUser(User user);

    /**
     * 根据用户名删除用户信息
     *
     * @param username 用户名
     */
    void deleteById(String username);

    /**
     * 多条件搜索用户数据
     *
     * @param searchMap 条件集合
     * @return 用户数据
     */
    List<User> findList(Map<String, Object> searchMap);

    /**
     * 多条件分页查询
     *
     * @param searchMap 条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页显示条数
     * @return 分页结果
     */
    Page<User> findPage(Map<String, Object> searchMap, Integer pageNum, Integer pageSize);

    /**
     * 更新用户积分
     *
     * @param task 任务类
     * @return 是否更新成功
     */
    int updateUserPoint(Task task);

    /**
     * 修改用户密码
     *
     * @param username 用户名
     * @param password 用户密码
     * @return true:成功 false:失败
     */
    boolean updatePassword(String username, String password);

    /**
     * 修改用户手机号
     *
     * @param username 用户名
     * @param newPhone 新手机号
     * @return 0:系统异常 1:修改成功 2:手机号已占用
     */
    int updatePhone(String username, String newPhone);
}