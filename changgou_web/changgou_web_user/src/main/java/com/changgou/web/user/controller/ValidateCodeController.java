package com.changgou.web.user.controller;

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
@RequestMapping("/wcode")
public class ValidateCodeController {
    /**
     * 随机生成6位数字验证码
     */
    public static String authCode = RandomUtil.randomNumbers( 6 );
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户更换手机号验证码
     *
     * @param telephone 用户手机号
     * @return 提示信息
     */
    @GetMapping("/sendResetPhoneNumber")
    public Result<Object> sendResetPhoneNumber(@RequestParam("telephone") String telephone) {
        if (StrUtil.isBlank( telephone )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "非法访问" ).build();
        }
        //发送短信
        boolean flag = SmsUtils.sendShortMessage( SmsUtils.RESET_PHONE_NUMBER_VALIDATE_CODE, telephone, authCode );
        if (flag) {
            //验证码发送成功,将登录验证码缓存到redis设置存活时时间(5分钟)
            redisTemplate.opsForValue().set( "resetPhoneNumber" + telephone, authCode, 5, TimeUnit.MINUTES );
            return Result.builder().flag( true ).code( StatusCode.OK ).message( "验证码发送成功" ).build();
        } else {
            //验证码发送失败
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "验证码发送失败,请检查手机号输入是否正确" ).build();
        }
    }
}