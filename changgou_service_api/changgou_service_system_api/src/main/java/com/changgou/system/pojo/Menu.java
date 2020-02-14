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
 * @Date: 2020/2/14 19:03
 * @Description: 菜单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 4273565984757128861L;

    /**
     * 菜单ID
     */
    @Id
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * URL
     */
    private String url;

    /**
     * 上级菜单ID
     */
    private String parentId;
}