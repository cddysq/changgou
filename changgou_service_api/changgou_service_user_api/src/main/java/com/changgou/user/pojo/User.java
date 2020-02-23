package com.changgou.user.pojo;

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
 * @Date: 2020/2/23 22:34
 * @Description: 用户实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = -5251118955001797821L;
    /**
     * 用户名
     */
    @Id
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 会员来源：1:PC，2：H5，3：Android，4：IOS
     */
    private String sourceType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 使用状态（1正常 0非正常）
     */
    private String status;

    /**
     * 头像地址
     */
    private String headPic;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 手机是否验证 （0否  1是）
     */
    private String isMobileCheck;

    /**
     * 邮箱是否检测（0否  1是）
     */
    private String isEmailCheck;

    /**
     * 性别，1男，0女
     */
    private String sex;

    /**
     * 会员等级
     */
    private Integer userLevel;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 经验值
     */
    private Integer experienceValue;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
}