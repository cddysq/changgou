package com.changgou.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 18:59
 * @Description: 登录日志类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_login_log")
public class LoginLog implements Serializable {
    private static final long serialVersionUID = -47515656355285103L;

    /**
     * 日志id
     */
    @Id
    private Integer id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 浏览器名
     */
    private String browserName;

    /**
     * 地区
     */
    private String location;

    /**
     * 登录时间
     */
    private Date loginTime;
}