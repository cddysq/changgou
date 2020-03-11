package com.changgou.logistics.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 20:40
 * @Description: 物流异常
 **/
@Getter
public class LogisticsException extends RuntimeException {
    private static final long serialVersionUID = 4835437126641668493L;

    private ExceptionMessage exceptionMessage;

    public LogisticsException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}