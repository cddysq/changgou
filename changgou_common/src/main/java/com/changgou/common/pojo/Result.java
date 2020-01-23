package com.changgou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Haotian
 * @Date: 2020/1/23 22:52
 * @Description: 返回结果实体类
 **/
@Data
@AllArgsConstructor
@Builder
public class Result<T> {

    /**
     * 是否成功
     */
    private boolean flag;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "执行成功";
    }
}