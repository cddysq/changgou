package com.changgou.oauth.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.common.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/3/8 13:41
 * @Description: 短信验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    /**
     * 随机生成6位数字验证码
     */
    public static String authCode = RandomUtil.randomNumbers( 6 );
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户注册验证码
     *
     * @param telephone 用户手机号
     * @return 提示信息
     */
    @GetMapping("/send4RRegister")
    public Result<Object> send4Login(@RequestParam("telephone") String telephone) {
        if (StrUtil.isBlank( telephone )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "非法访问" ).build();
        }
        //发送前校验手机号是否被抢注
        Boolean username = redisTemplate.boundSetOps( "username" ).isMember( telephone );
        if (username) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "此手机号已被注册" ).build();
        }
        //发送短信
        boolean flag = SmsUtils.sendShortMessage( SmsUtils.REGISTRY_VALIDATE_CODE, telephone, authCode );
        if (flag) {
            //验证码发送成功,将登录验证码缓存到redis设置存活时时间(5分钟)
            redisTemplate.opsForValue().set( "register" + telephone, authCode, 5, TimeUnit.MINUTES );
            return Result.builder().flag( true ).code( StatusCode.OK ).message( "验证码发送成功" ).build();
        } else {
            //验证码发送失败
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "验证码发送失败,请检查手机号输入是否正确" ).build();
        }
    }
}