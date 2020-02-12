package com.changgou.service.goods.exception;

import com.changgou.common.exception.ExceptionMessage;
import lombok.Getter;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 16:15
 * @Description: 品牌异常
 */
@Getter
public class GoodsException extends RuntimeException {
    private static final long serialVersionUID = 8482500389681789675L;

    private ExceptionMessage exceptionMessage;

    public GoodsException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}