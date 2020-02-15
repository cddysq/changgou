package com.changgou.system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 20:09
 * @Description: JWT工具类
 */
public class JwtUtil {
    /**
     * 有效期 4*60*60*1000  四个小时
     */
    public static final Long JWT_TTL = 14400000L;

    /**
     * 设置秘钥明文
     */
    public static final String JWT_KEY = "changgou";

    /**
     * 创建Jwt令牌
     *
     * @param id        唯一的ID
     * @param subject   主题  可以是JSON数据
     * @param ttlMillis 过期时间
     * @return 令牌
     */
    public static String createJwt(String id, String subject, Long ttlMillis) {
        //加密方式
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date( nowMillis );
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date( expMillis );

        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                //设置唯一编号
                .setId( id )
                //设置主体内容
                .setSubject( subject )
                // 签发者
                .setIssuer( "sakura" )
                // 签发时间
                .setIssuedAt( now )
                //使用HS256对称加密算法签名, 第二个参数为秘钥
                .signWith( signatureAlgorithm, secretKey )
                // 设置过期时间
                .setExpiration( expDate );
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return 加密后签名
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode( JwtUtil.JWT_KEY );
        return new SecretKeySpec( encodedKey, 0, encodedKey.length, "AES" );
    }

    /**
     * 解析令牌
     *
     * @param jwt 令牌
     * @return 内容体
     */
    public static Claims parseJwt(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                //设置签名
                .setSigningKey( secretKey )
                //设置令牌
                .parseClaimsJws( jwt )
                //获取内容
                .getBody();
    }
}