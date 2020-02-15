package com.changgou.service.system.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * @Author: Haotian
 * @Date: 2020/2/15 16:49
 * @Description: 用户异常
 **/
@Getter
public class AdminException extends RuntimeException {
    private static final long serialVersionUID = 8482500389681789675L;

    private ExceptionMessage exceptionMessage;

    public AdminException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}