package com.changgou.system.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 16:19
 * @Description: ip过滤器
 */
@Component
@Slf4j
public class IpFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //TODO: 2020/2/14 17:33 对特殊ip拦截
        String address = Objects.requireNonNull( request.getRemoteAddress() ).getHostName();
        log.info( "当前访问 ip:{}", address );
        return chain.filter( exchange );
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
