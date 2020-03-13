package com.changgou.oauth.controller;

import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.oauth.constant.OauthStatusEnum;
import com.changgou.oauth.exception.OauthException;
import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import com.changgou.oauth.util.CookieUtil;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: Haotian
 * @Date: 2020/2/24 22:13
 * @Description: 权限认证接口
 */
@Controller
@RequestMapping("/oauth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserFeign userFeign;
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 登录提示
     */
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

    /**
     * 跳转登录
     *
     * @return 登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value = "FROM", required = false, defaultValue = "") String from, Model model) {
        model.addAttribute( "from", from );
        return "login";
    }

    /**
     * 跳转注册页面
     *
     * @return 注册页面
     */
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    /**
     * 用户注册
     *
     * @return 提示信息
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<Object> register(@RequestBody User user,
                                   @RequestParam("checkCode") String checkCode, @RequestParam("smsCode") String smsCode, HttpServletRequest request) {
        //校验输入数据
        if (StrUtil.isBlank( checkCode )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "请输入图形验证码" ).build();
        }
        //校验图形验证码
        boolean flag = this.checkUserCode( request, checkCode );
        if (!flag) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "图形验证码输入错误，请重试" ).build();
        }
        String redisCode = redisTemplate.opsForValue().get( "register" + user.getPhone() );
        //校验手机验证码
        if (StrUtil.isEmpty( smsCode ) || !smsCode.equals( redisCode )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "手机验证码输入错误，请重试" ).build();
        }
        //注册用户
        return userFeign.addUser( user );
    }

    /**
     * 保存cookie
     *
     * @param jti      jti
     * @param response response 对象
     */
    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie( response, cookieDomain, "/", "uid", jti, cookieMaxAge, false );
    }

    /**
     * 验证码校验
     *
     * @param checkCode 用户输入验证码
     * @return true 验证码核对成功 false 验证码检验失败
     */
    private boolean checkUserCode(HttpServletRequest request, String checkCode) {
        //从session中获取验证码
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute( "CAPTCHA" );
        //获取后立马销毁session中的验证码
        session.removeAttribute( "CAPTCHA" );
        //验证码不存在或用户输入和session中的不一致
        return !StrUtil.isEmpty( checkCode ) && checkCode.equalsIgnoreCase( sessionCaptcha );
    }
}