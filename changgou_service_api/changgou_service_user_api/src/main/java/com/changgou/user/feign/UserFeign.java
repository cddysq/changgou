package com.changgou.user.feign;

import com.changgou.common.pojo.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 22:40
 * @Description: 用户对外接口
 **/
@FeignClient(name = "user")
public interface UserFeign {

    /**
     * 查找用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/user/load/{username}")
    User findUserInfo(@PathVariable("username") String username);

    /**
     * 新增用户
     *
     * @param user 新用户数据
     * @return 新增结果
     */
    @PostMapping("/user/add")
    Result<Object> addUser(@RequestBody User user);

    /**
     * 修改用户密码
     *
     * @param password 用户密码
     * @return 提示信息
     */
    @PutMapping("/user/resetPassword")
    Result<Object> resetPassword(@RequestBody String password);

    /**
     * 根据用户名查询用户数据
     *
     * @return 用户信息
     */
    @GetMapping("/user/findUser")
    Result<User> findById();

    /**
     * 修改用户手机号
     *
     * @param phone 新手机号
     * @return 提示信息
     */
    @PutMapping("/user/resetPhone")
    Result<Object> resetPhone(@RequestParam("phone") String phone);
}