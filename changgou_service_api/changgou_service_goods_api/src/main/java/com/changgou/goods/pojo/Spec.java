package com.changgou.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/1/25 17:17
 * @Description: 规格实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_spec")
public class Spec implements Serializable {
    private static final long serialVersionUID = -43670319100788900L;
    /**
     * 规格id
     */
    @Id
    private Integer id;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格选项
     */
    private String options;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 模板ID
     */
    private Integer templateId;
}