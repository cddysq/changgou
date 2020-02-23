package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 20:18
 * @Description: 创建令牌测试
 */
public class CreateJwtTest {
    @Test
    public void createJwt() {
        //1.指定私钥的位置
        ClassPathResource classPathResource = new ClassPathResource( "changgou.jks" );
        //2.指定秘钥库的密码
        String keyPassword = "changgou";
        //3.创建密匙工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory( classPathResource, keyPassword.toCharArray() );
        //4.基于工厂获取私钥
        String alias = "changgou";
        String password = "changgou";
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair( alias, password.toCharArray() );
        //将私钥转为ras私钥
        RSAPrivateKey rasPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        //5.生成jwt
        Map<String, String> map = new HashMap<>();
        map.put( "name", "艾莉丝" );
        map.put( "age", "18" );

        Jwt jwt = JwtHelper.encode( JSON.toJSONString( map ), new RsaSigner( rasPrivateKey ) );
        String encoded = jwt.getEncoded();
        System.out.println( encoded );
    }
}
