package com.changgou.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/2/14 19:08
 * @Description: 角色实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_role")
public class Role implements Serializable {
    private static final long serialVersionUID = 4485569683319776006L;

    /**
     * 角色ID
     */
    @Id
    private Integer id;

    /**
     * 角色名称
     */
    private String name;
}