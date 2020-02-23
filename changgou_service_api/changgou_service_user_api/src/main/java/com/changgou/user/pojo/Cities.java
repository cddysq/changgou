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
 * @Description: 城市表
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_cities")
public class Cities implements Serializable {
    private static final long serialVersionUID = -7276153360691574006L;
    /**
     * 城市ID
     */
    @Id
    private String cityid;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 省份ID
     */
    private String provinceid;
}