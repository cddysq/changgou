package com.changgou.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/2/26 18:28
 * @Description: 退货原因实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_return_cause")
public class ReturnCause implements Serializable {
    private static final long serialVersionUID = -3117494403331385708L;
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * 原因
     */
    private String cause;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 是否启用
     */
    private String status;
}