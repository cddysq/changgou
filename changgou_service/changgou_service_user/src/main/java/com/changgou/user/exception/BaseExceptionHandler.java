package com.changgou.user.exception;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Haotian
 * @Date: 2020/1/24 20:10
 * @Description: 公共异常处理
 */
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> error(Exception e) {
        if (e instanceof UserException) {
            //异常类型为自定义异常，抛出异常信息
            UserException userException = (UserException) e;
            ExceptionMessage message = userException.getExceptionMessage();
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