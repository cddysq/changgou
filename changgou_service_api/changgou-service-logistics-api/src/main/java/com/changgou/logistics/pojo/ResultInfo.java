package com.changgou.logistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 20:03
 * @Description: 返回信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultInfo implements Serializable {
    private static final long serialVersionUID = 31090954775038850L;
    /**
     * 订单号
     */
    private String number;

    /**
     * 包裹所属物流公司名
     */
    private String type;

    /**
     * 包裹途径地名等信息集合
     */
    private List<AddressInfo> list;

    /**
     * 0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收
     */
    private int deliverystatus;

    /**
     * 1.是否签收
     */
    private int issign;

    /**
     * 途径城市
     */
    private List<String> citys;

    /**
     * 快递公司名称
     */
    private String expName;

    /**
     * 快递公司官网
     */
    private String expSite;

    /**
     * 快递公司电话
     */
    private String expPhone;

    /**
     * 快递公司logo
     */
    private String logo;

    /**
     * 快递员 或 快递站(没有则为空)
     */
    private String courier;

    /**
     * 快递员电话 (没有则为空)
     */
    private String courierPhone;

    /**
     * 快递轨迹信息最新时间
     */
    private String updateTime;

    /**
     * 发货到收货消耗时长 (截止最新轨迹)
     */
    private String takeTime;
}
