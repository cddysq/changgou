package com.changgou.web.gateway.service;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 * @Author: Haotian
 * @Date: 2020/2/25 16:28
 */
@Service
public class AuthService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 从 cookie 中获取 jti 的值
     *
     * @param request 请求对象
     * @return jti
     */
    public String getJtiFromCookie(ServerHttpRequest request) {
        //cookie 中 jti 对应的键位为 uid
        HttpCookie httpCookie = request.getCookies().getFirst( "uid" );
        if (ObjectUtil.isNotEmpty( httpCookie )) {
            return httpCookie.getValue();
        }
        return null;
    }

    /**
     * 使用 jti 从 redis 中拿到令牌
     *
     * @param jti jti
     * @return jwt 令牌
     */
    public String getJwtFromRedis(String jti) {
        return stringRedisTemplate.boundValueOps( jti ).get();
    }
}