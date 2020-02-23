package com.changgou.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/2/23 22:32
 * @Description: 地址实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_address")
public class Address implements Serializable {
    private static final long serialVersionUID = 6828041023117586332L;
    /**
     * 地址 id
     */
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 省
     */
    private String provinceid;

    /**
     * 市
     */
    private String cityid;

    /**
     * 县/区
     */
    private String areaid;

    /**
     * 电话
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 是否是默认 1默认 0否
     */
    private String isDefault;

    /**
     * 别名
     */
    private String alias;
}