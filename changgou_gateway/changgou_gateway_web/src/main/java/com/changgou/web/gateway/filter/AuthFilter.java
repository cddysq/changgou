package com.changgou.web.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.changgou.web.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Haotian
 * @Date: 2020/2/25 16:20
 * @Description: 身份认证过滤器
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final String LOGIN_URI = "/api/oauth/login";
    private static final String TO_LOGIN_URL = "http://localhost:9102/api/oauth/toLogin";
    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        //1.判断当前请求路径是否为登录请求,如果是,则直接放行
        String path = request.getURI().getPath();
        if (LOGIN_URI.equals( path ) || !UrlFilter.hasAuthorize( path )) {
            //是登录请求,进行放行
            return chain.filter( exchange );
        }

        //2.从cookie中获取jti的值,如果该值不存在,拒绝本次访问
        String jti = authService.getJtiFromCookie( request );
        if (StrUtil.isEmpty( jti )) {
            //为空 拒绝访问
            /*response.setStatusCode( HttpStatus.UNAUTHORIZED );
            return response.setComplete();*/
            //跳转登录页面
            return this.toLoginPage( TO_LOGIN_URL + "?FROM=" + request.getURI().getPath(), exchange );
        }

        //3.从redis中获取jwt的值,如果该值不存在,拒绝本次访问
        String jwt = authService.getJwtFromRedis( jti );
        if (StrUtil.isEmpty( jwt )) {
            return this.toLoginPage( TO_LOGIN_URL + "?FROM=" + request.getURI().getPath(), exchange );
        }

        //4.对当前的请求对象进行增强,让它携带令牌的信息
        request.mutate().header( "Authorization", "Bearer " + jwt );
        return chain.filter( exchange );
    }

    /**
     * 跳转登录页面
     *
     * @param loginUrl 登录url
     * @param exchange 响应对象
     * @return 登录页面
     */
    private Mono<Void> toLoginPage(String loginUrl, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode( HttpStatus.MOVED_TEMPORARILY );
        response.getHeaders().set( "Location", loginUrl );
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
