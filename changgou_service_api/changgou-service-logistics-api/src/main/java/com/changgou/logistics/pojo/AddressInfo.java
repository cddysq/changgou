package com.changgou.logistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 19:59
 * @Description: 物流途径地址经度信息封装实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressInfo implements Serializable {
    private static final long serialVersionUID = -5179737134986035473L;
    /**
     * 当前时间
     */
    private String time;

    /**
     * 包裹状态
     */
    private String status;

    /**
     * 途径城市经纬度
     */
    private List<String> cityLoeLae;
}