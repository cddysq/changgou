package com.changgou.user.feign;

import com.changgou.common.pojo.Result;
import com.changgou.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}