package com.changgou.user.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 17:02
 * @Description: 用户异常信息
 */
@AllArgsConstructor
public enum UserStatusEnum implements ExceptionMessage {
    USER_REPEAT(false, StatusCode.ERROR,"用户已经存在" );
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