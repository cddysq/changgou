package com.changgou.web.user.controller;

import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.user.feign.UserFeign;
import com.changgou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Haotian
 * @Date: 2020/3/9 10:41
 * @Description: 用户安全管理
 */
@Controller
@RequestMapping("/wuser")
public class UserSecurityController {
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 跳转安全管理界面
     *
     * @return 密码修改页面
     */
    @RequestMapping("/toSecurity")
    public String toSecurity(Model model) {
        Result<User> user = userFeign.findById();
        model.addAttribute( "telephone", user.getData().getPhone() );
        return "center-setting-safe";
    }

    /**
     * 跳转绑定新手机号界面
     *
     * @return 新手机号修改页面
     */
    @RequestMapping("/toNewPhone")
    public String toRegister() {
        return "center-setting-address-phone";
    }

    /**
     * 跳转绑定手机号成功界面
     *
     * @return 新手机号修改成功页面
     */
    @RequestMapping("/toSuccess")
    public String toSuccess() {
        return "center-setting-address-complete";
    }

    /**
     * 修改用户密码
     *
     * @param password 用户密码
     * @return 提示信息
     */
    @PutMapping("/resetPassword/{password}")
    @ResponseBody
    public Result<Object> resetPassword(@PathVariable("password") String password) {
        if (StrUtil.isBlank( password )) {
            return Result.builder()
                    .flag( false )
                    .code( StatusCode.ERROR )
                    .message( "非法访问" ).build();
        }
        return userFeign.resetPassword( password );
    }

    /**
     * 修改用户手机号
     *
     * @param phone 新手机号
     * @return 提示信息
     */
    @PutMapping("/resetPhone/{phone}")
    @ResponseBody
    public Result<Object> resetPhone(@PathVariable("phone") String phone) {
        if (StrUtil.isBlank( phone )) {
            return Result.builder()
                    .flag( false )
                    .code( StatusCode.ERROR )
                    .message( "数据非法" ).build();
        }
        return userFeign.resetPhone( phone );
    }

    /**
     * 校验手机验证码
     *
     * @param smsCode 验证码
     * @param phone   手机号
     * @return 提示信息
     */
    @GetMapping("/checkSmsCode")
    @ResponseBody
    public Result<Object> checkSmsCode(@RequestParam("smsCode") String smsCode, @RequestParam("phone") String phone) {
        if (StrUtil.isBlank( smsCode ) || StrUtil.isBlank( phone )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "非法数据" ).build();
        }
        String redisCode = redisTemplate.opsForValue().get( "resetPhoneNumber" + phone );
        //校验手机验证码
        if (StrUtil.isEmpty( redisCode ) || !smsCode.equals( redisCode )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "手机验证码输入错误或已过期，请重试" ).build();
        }
        return Result.builder().flag( true ).code( StatusCode.OK ).message( "手机号验证成功" ).build();
    }
}