package com.changgou.order.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 16:54
 * @Description: 订单异常信息
 **/
@AllArgsConstructor
public enum OrderStatusEnum implements ExceptionMessage {
    NOT_FOUND_ORDER( false, StatusCode.ERROR, "订单不存在" ),
    ORDER_IS_DELIVERY( false, StatusCode.ERROR, "订单不存在" );
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
