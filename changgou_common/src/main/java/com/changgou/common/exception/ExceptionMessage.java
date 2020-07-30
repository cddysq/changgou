package com.changgou.common.exception;

/**
 * 异常返回信息
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:23
 **/
public interface ExceptionMessage {
    /**
     * 获取处理是否成功
     *
     * @return true or false
     */
    Boolean isFlag();

    /**
     * 获取错误码
     *
     * @return 错误状态码
     */
    Integer getCode();

    /**
     * 获取错误信息
     *
     * @return 错误提示
     */
    String getMessage();
}
