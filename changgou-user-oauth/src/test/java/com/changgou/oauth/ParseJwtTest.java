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
    private static final String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi6Im-6I6J5LidIiwiYWdlIjoiMTgifQ.IBLmnYOFv_i45-iXL0IPIunjcPbBRFVh0JWE68ZpsdMajbpnisUVxC77fIxMSZmDYrienE7mehloY_QYwvLHg-NwjgXWeCQyBDSZpQpxu5-pFNG-TVmjDfr23cDL3Rd3YPz_Azktu6nyRAwjr1S5FL7DVPyPN6JQEWVXvt1qO5H5lhAqnehUc8LRAiARf2clxZubPh6yjsW0ziHGZYF4MzOJZ8mTd8LZjodO3yPqOkTLBVzumMdmLTwMUjDKOR98ytqS0wtG-51cckZ6IXMf1jrtLWYJ_I8d9V4jFvne9ciCPBUOInDRgoc1AA2NInhF6M2W25Q5JvhSLBwNvFGBZA";
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