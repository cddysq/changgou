package com.changgou.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(-1)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 忽略安全拦截的URL
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers( "/oauth/login", "/oauth/logout", "/oauth/toLogin", "/login.html",
                "/oauth/toRegister", "/oauth/register", "/register.html",
                "/checkCode", "/validateCode/send4RRegister", "/css/**", "/data/**", "/fonts/**", "/img/**", "/js/**" );
    }

    /***
     * 创建授权管理认证对象
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /***
     * 采用BCryptPasswordEncoder对密码进行编码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()        //启用Http基本身份验证
                .and()
                .logout()
                //清除指定cookie
                .deleteCookies( "uid" )
                //退出成功跳转登录
                .logoutSuccessUrl( "http://localhost:9102/api/oauth/toLogin" )
                .and()
                .formLogin()       //启用表单身份验证
                .and()
                .authorizeRequests()    //限制基于Request请求访问
                .anyRequest()
                .authenticated();       //其他请求都需要经过验证
        //开启表单登录
        http.formLogin()
                //设置访问登录页面的路径
                .loginPage( "/oauth/toLogin" )
                //设置执行登录操作的路径
                .loginProcessingUrl( "/oauth/login" );
    }
}