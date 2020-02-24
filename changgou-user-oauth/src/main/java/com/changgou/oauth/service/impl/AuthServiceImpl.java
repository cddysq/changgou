package com.changgou.oauth.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/2/24 19:57
 * @Description: 身份验证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${auth.ttl}")
    private long ttl;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //1.申请令牌
        ServiceInstance choose = loadBalancerClient.choose( "user-auth" );
        URI uri = choose.getUri();
        String url = uri + "/oauth/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add( "grant_type", "password" );
        body.add( "username", username );
        body.add( "password", password );

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add( "Authorization", this.getHttpBasic( clientId, clientSecret ) );

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>( body, headers );

        restTemplate.setErrorHandler( new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError( response );
                }

            }
        } );
        ResponseEntity<Map> responseEntity = restTemplate.exchange( url, HttpMethod.POST, requestEntity, Map.class );
        Map map = responseEntity.getBody();
        if (ObjectUtil.isEmpty( map )) {
            //申请令牌失败
            throw new RuntimeException( "申请令牌失败" );
        }
        String accessToken = Convert.toStr( map.get( "access_token" ) );
        String refreshToken = Convert.toStr( map.get( "refresh_token" ) );
        String jti = Convert.toStr( map.get( "jti" ) );
        if (StrUtil.isEmpty( accessToken ) || StrUtil.isEmpty( refreshToken ) || StrUtil.isEmpty( jti )) {
            throw new RuntimeException( "申请令牌失败" );
        }

        //2.封装结果数据
        AuthToken authToken = AuthToken.builder()
                .accessToken( accessToken )
                .refreshToken( refreshToken )
                .jti( jti ).build();

        //3.将jtl作为redis中的key,将jwt作为redis中的value
        stringRedisTemplate.boundValueOps( authToken.getJti() ).set( authToken.getAccessToken(), ttl, TimeUnit.SECONDS );

        return authToken;
    }

    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode( value.getBytes() );
        return "Basic " + new String( encode );
    }
}