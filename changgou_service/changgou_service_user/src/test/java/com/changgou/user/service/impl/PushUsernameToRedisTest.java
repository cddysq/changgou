package com.changgou.user.service.impl;

import com.changgou.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @Author: Haotian
 * @Date: 2020/3/7 22:47
 * @Description: 用户名预加载
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PushUsernameToRedisTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserService userService;

    @Test
    public void push() {
        userService.findAll().forEach( s -> redisTemplate.boundSetOps( "username" ).add( s.getUsername() ) );
    }

}