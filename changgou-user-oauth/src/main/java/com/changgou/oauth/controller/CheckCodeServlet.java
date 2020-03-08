package com.changgou.oauth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Haotian
 * @Date: 2020/3/8 14:07
 * @Description: HuTool生成验证码
 */
@RestController
@RequestMapping("/checkCode")
public class CheckCodeServlet {
    @GetMapping
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //服务器通知浏览器不要缓存
        response.setHeader( "pragma", "no-cache" );
        response.setHeader( "cache-control", "no-cache" );
        response.setHeader( "expires", "0" );
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //使用CaptchaUtil创建验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha( 100, 40, 4, 40 );
        //获取验证码
        String checkCode = lineCaptcha.getCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute( "CAPTCHA", checkCode );
        //将验证码输出到浏览器
        lineCaptcha.write( outputStream );
        //关闭输出流
        outputStream.close();
    }

    @PostMapping
    public void checkCode() {
        this.checkCode();
    }
}