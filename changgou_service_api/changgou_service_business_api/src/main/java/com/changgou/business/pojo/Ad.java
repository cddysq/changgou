package com.changgou.business.pojo;

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
 * @Date: 2020/2/18 20:08
 * @Description: 广告实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_ad")
public class Ad implements Serializable {
    private static final long serialVersionUID = -4578536765474904205L;
    /**
     * 广告ID
     */
    @Id
    private Integer id;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 广告位置
     */
    private String position;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 到期时间
     */
    private Date endTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 图片地址
     */
    private String image;

    /**
     * URL
     */
    private String url;

    /**
     * 备注
     */
    private String remarks;
}