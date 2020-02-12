package com.changgou.common.exception;

/**
 * @Author: Haotian
 * @Date: 2020/2/12 16:21
 * @Description: 异常返回信息
 */
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
