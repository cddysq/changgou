package com.changgou.user.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 16:15
 * @Description: 用户异常
 */
@Getter
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 8482500389681789675L;

    private ExceptionMessage exceptionMessage;

    public UserException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}