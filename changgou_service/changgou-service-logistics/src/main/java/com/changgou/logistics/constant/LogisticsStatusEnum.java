package com.changgou.logistics.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 20:39
 * @Description: 物流服务异常信息
 **/
@AllArgsConstructor
public enum LogisticsStatusEnum implements ExceptionMessage {
    SYSTEM_ERROR( false, StatusCode.ERROR, "系统异常，请稍后重试" ),
    ;
    private boolean flag;
    private Integer code;
    private String message;

    @Override
    public Boolean isFlag() {
        return this.flag;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}