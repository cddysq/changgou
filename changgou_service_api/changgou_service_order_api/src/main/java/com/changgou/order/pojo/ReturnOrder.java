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
 * @Date: 2020/2/26 18:24
 * @Description: 退货实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_return_order")
public class ReturnOrder implements Serializable {
    private static final long serialVersionUID = 7442173584469008912L;
    /**
     * 服务单号
     */
    @Id
    private String id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 申请时间
     */
    private java.util.Date applyTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系人手机
     */
    private String linkmanMobile;

    /**
     * 类型
     */
    private String type;

    /**
     * 退款金额
     */
    private Integer returnMoney;

    /**
     * 是否退运费
     */
    private String isReturnFreight;

    /**
     * 申请状态
     */
    private String status;

    /**
     * 处理时间
     */
    private java.util.Date disposeTime;

    /**
     * 退货退款原因
     */
    private Integer returnCause;

    /**
     * 凭证图片
     */
    private String evidence;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 处理备注
     */
    private String remark;

    /**
     * 管理员id
     */
    private Integer adminId;
}