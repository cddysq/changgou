package com.changgou.oauth.util;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserJwt extends User {
    private static final long serialVersionUID = 7674408126486856512L;
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名字
     */
    private String name;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super( username, password, authorities );
    }

}