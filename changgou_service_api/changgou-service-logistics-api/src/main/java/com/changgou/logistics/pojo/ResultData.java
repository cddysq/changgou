package com.changgou.logistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 20:16
 * @Description: 物流接口最终返回所有信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultData implements Serializable {
    private static final long serialVersionUID = 3850601758738942879L;
    /**
     * 接口调用状态码  status 0:正常查询 201:快递单号错误 203:快递公司不存在 204:快递公司识别失败 205:没有信息 207:该单号被限制，错误单号
     */
    private String status;

    /**
     * 调用返回信息
     */
    private String msg;

    /**
     * 数据实体类
     */
    private ResultInfo result;
}