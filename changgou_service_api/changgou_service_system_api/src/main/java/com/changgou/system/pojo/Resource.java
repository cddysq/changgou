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
 * @Date: 2020/2/14 19:06
 * @Description: 资源类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_resource")
public class Resource implements Serializable {
    private static final long serialVersionUID = 3524883686464965472L;

    /**
     * 资源id
     */
    @Id
    private Integer id;

    /**
     * res_key
     */
    private String resKey;

    /**
     * res_name
     */
    private String resName;

    /**
     * parent_id
     */
    private Integer parentId;
}