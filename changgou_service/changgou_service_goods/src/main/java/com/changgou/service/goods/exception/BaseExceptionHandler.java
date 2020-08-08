package com.changgou.service.goods.exception;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公共异常处理
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 16:00
 **/
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> error(Exception e) {
        if (e instanceof GoodsException) {
            // 异常类型为自定义异常，抛出异常信息
            GoodsException goodsException = (GoodsException) e;
            ExceptionMessage message = goodsException.getExceptionMessage();
            return Result.builder()
                    .flag( message.isFlag() )
                    .code( message.getCode() )
                    .message( message.getMessage() ).build();
        }
        log.error( e.getMessage() );
        return Result.builder()
                .flag( false )
                .code( StatusCode.ERROR )
                .message( e.getMessage() ).build();
    }
}