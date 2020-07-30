package com.changgou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 返回结果实体类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:24
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