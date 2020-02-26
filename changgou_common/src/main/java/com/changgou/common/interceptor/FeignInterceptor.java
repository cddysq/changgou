package com.changgou.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 22:42
 * @Description: feign 拦截器 传递令牌
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取请求中的令牌进行传递
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            //获取request对象
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            //得到所有请求头
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                //拿到每一个请求头
                String headerName = headerNames.nextElement();
                //对比请求头
                if ("authorization".equals( headerName )) {
                    //得到jwt
                    String headerValue = request.getHeader( headerName );
                    //传递
                    requestTemplate.header( headerName, headerValue );
                }
            }
        }
    }
}