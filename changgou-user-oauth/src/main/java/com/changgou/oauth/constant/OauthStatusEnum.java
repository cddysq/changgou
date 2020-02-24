package com.changgou.oauth.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 17:02
 * @Description: 权限认证异常信息
 */
@AllArgsConstructor
public enum OauthStatusEnum implements ExceptionMessage {
    APPLY_FOR_TOKEN_ERROR( false, StatusCode.ERROR, "申请令牌失败" ),
    USERNAME_IS_NULL( false, StatusCode.ERROR, "请输入用户名" ),
    PASSWORD_IS_NULL( false, StatusCode.ERROR, "请输入密码" );
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
