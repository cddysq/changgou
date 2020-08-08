package com.changgou.service.goods.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * 品牌异常
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:00
 **/
@Getter
public class GoodsException extends RuntimeException {
    private static final long serialVersionUID = 8482500389681789675L;

    private ExceptionMessage exceptionMessage;

    public GoodsException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}