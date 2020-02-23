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
 * @Date: 2020/2/23 22:31
 * @Description: 区表
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_areas")
public class Areas implements Serializable {
    private static final long serialVersionUID = 3938672881092734261L;
    /**
     * 区域ID
     */
    @Id
    private String areaid;

    /**
     * 区域名称
     */
    private String area;

    /**
     * 城市ID
     */
    private String cityid;
}