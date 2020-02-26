package com.changgou.order.pojo;

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
 * @Date: 2020/2/26 18:17
 * @Description: log实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "undo_log")
public class Log implements Serializable {
    private static final long serialVersionUID = -4156006968086765568L;
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * branch_id
     */
    private Long branchId;

    /**
     * xid
     */
    private String xid;

    /**
     * rollback_info
     */
    private byte[] rollbackInfo;

    /**
     * log_status
     */
    private Integer logStatus;

    /**
     * log_created
     */
    private Date logCreated;

    /**
     * log_modified
     */
    private Date logModified;

    /**
     * ext
     */
    private String ext;
}