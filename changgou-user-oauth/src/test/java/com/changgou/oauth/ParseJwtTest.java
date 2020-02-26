package com.changgou.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 20:37
 * @Description: 使用公钥解析jwt
 */
public class ParseJwtTest {
    /**
     * jwt
     */
    private static final String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4Mjc0OTczMSwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiJmMzU2YzFkNC1iZDczLTQ1MjAtYjkzMS1lMzkxZjU3NDVjNWIiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiaGVpbWEifQ.I6u_HOC0W140EVTU0lggda3GzVusZ_EB3gikg_7HYYoZdIpBWvTAD821FcKmJm2E26ZjTTE2MoYPlcUvxkritll6F5JRTpgbQkwMVhfNG1x_ZVjjikNCeLVVJGaT0Ki8pw23FJBQJHSkA8_tCfT_9GIIvDhMZJTLbNcbuyhqNDmMIoGYwAgR1KVVeAlOL4EEyJlQtASgQTS1lCJHpfOePRpO1TuldST8Y1HWLpyvqn3QWwImaQ3SsQfZOPy3PQegLsi4TCLxo8g7G5DUKsl0XliblsTQtFZVd9z4H01LCCDH28QL5TW1iamHovi99f0pduldP1Z97BpMhiZNPnV5nw";
    /**
     * 公钥
     */
    private static final String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

    @Test
    public void parseJwt() {
        Jwt token = JwtHelper.decodeAndVerify( ParseJwtTest.jwt, new RsaVerifier( publicKey ) );
        String claims = token.getClaims();
        System.out.println( claims );

    }
}