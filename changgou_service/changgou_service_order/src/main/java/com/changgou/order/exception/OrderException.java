package com.changgou.order.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * @Author: Haotian
 * @Date: 2020/3/3 16:55
 * @Description: 订单异常
 **/
@Getter
public class OrderException extends RuntimeException {
    private static final long serialVersionUID = 4835437126641668493L;

    private ExceptionMessage exceptionMessage;

    public OrderException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}