package com.changgou.oauth.controller;

import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.oauth.constant.OauthStatusEnum;
import com.changgou.oauth.exception.OauthException;
import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import com.changgou.oauth.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Haotian
 * @Date: 2020/2/24 22:13
 * @Description: 权限认证接口
 */
@Controller
@RequestMapping("/oauth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;


    @RequestMapping("/login")
    @ResponseBody
    public Result<Object> login(String username, String password, HttpServletResponse response) {
        //校验参数
        if (StrUtil.isBlank( username )) {
            throw new OauthException( OauthStatusEnum.USERNAME_IS_NULL );
        }
        if (StrUtil.isBlank( password )) {
            throw new OauthException( OauthStatusEnum.PASSWORD_IS_NULL );
        }
        //申请令牌
        AuthToken authToken = authService.login( username, password, clientId, clientSecret );
        //将jti值存入cookie中
        this.saveJtiToCookie( authToken.getJti(), response );
        //返回结果
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "登录成功" )
                .data( authToken.getJti() ).build();
    }

    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie( response, cookieDomain, "/", "uid", jti, cookieMaxAge, false );
    }
}
