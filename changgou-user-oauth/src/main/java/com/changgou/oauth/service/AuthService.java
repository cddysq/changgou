package com.changgou.oauth.service;

import com.changgou.oauth.util.AuthToken;

/**
 * @Author: Haotian
 * @Date: 2020/2/24 19:53
 * @Description: 身份验证服务
 */
public interface AuthService {
    /**
     * 登录校验获取token
     *
     * @param username     用户名
     * @param password     用户密码
     * @param clientId     客户端id
     * @param clientSecret 客户端秘钥
     * @return token
     */
    AuthToken login(String username, String password, String clientId, String clientSecret);
}