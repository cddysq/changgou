package com.changgou.seckill.web.aspect;

import com.alibaba.fastjson.JSON;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Haotian
 * @Date: 2020/3/7 20:13
 * @Description: 切面类
 */
@Component
@Scope
@Aspect
public class AccessLimitAop {
    /**
     * 设置令牌的生成速率,每秒生成两个令牌存入桶中
     */
    private RateLimiter rateLimiter = RateLimiter.create( 2.0 );
    @Resource
    private HttpServletResponse response;

    @Pointcut("@annotation(com.changgou.seckill.web.aspect.AccessLimit)")
    public void limit() {

    }

    @Around("limit()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        //判定访问是否通过
        boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        if (flag) {
            //允许访问
            try {
                obj = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            //拒绝访问
            String errorMessage = JSON.toJSONString( Result.builder().flag( false ).code( StatusCode.ERROR ).message( "fail" ) );
            //将信息返回到客户端
            this.outMessage( response, errorMessage );
        }
        return obj;
    }

    /**
     * 将错误信息输出到客户端
     *
     * @param response     响应对象
     * @param errorMessage 错误信息
     */
    private void outMessage(HttpServletResponse response, String errorMessage) {
        ServletOutputStream outputStream = null;
        try {
            response.setContentType( "application/json;charset=UTF-8" );
            outputStream = response.getOutputStream();
            outputStream.write( errorMessage.getBytes( "UTF-8" ) );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}