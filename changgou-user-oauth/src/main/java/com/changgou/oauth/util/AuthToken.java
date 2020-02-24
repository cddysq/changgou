package com.changgou.oauth.util;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthToken implements Serializable {

    private static final long serialVersionUID = -8566978856822903174L;
    /**
     * 令牌信息
     */
    String accessToken;

    /**
     * 刷新token(refresh_token)
     */
    String refreshToken;

    /**
     * jwt短令牌
     */
    String jti;
}