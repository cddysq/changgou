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
 * @Date: 2020/2/23 22:29
 * @Description: 省份表
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_provinces")
public class Provinces implements Serializable {
    private static final long serialVersionUID = -638626223602916722L;
    /**
     * 省份ID
     */
    @Id
    private String provinceid;

    /**
     * 省份名称
     */
    private String province;
}