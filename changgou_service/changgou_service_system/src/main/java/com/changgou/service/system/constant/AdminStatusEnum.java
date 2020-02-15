package com.changgou.service.system.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 17:02
 * @Description: 用户异常信息
 */
@AllArgsConstructor
public enum AdminStatusEnum implements ExceptionMessage {
    INSERT_NULL( false, StatusCode.ERROR, "请不要输入空白用户名或密码" ),
    ADMIN_EXIST( false, StatusCode.ERROR, "用户已存在" ),
    ADMIN_DISABLE( false, StatusCode.ERROR, "此用户账号已被禁用" );
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
