package com.changgou.service.goods.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * @Author: Haotian
 * @Date: 2020/2/16 19:49
 * @Description: 商品信息
 **/
@AllArgsConstructor
public enum GoodsStatusEnum implements ExceptionMessage {
    GOODS_VANISH( false, StatusCode.ERROR, "当前商品不存在" ),
    GOODS_NOT_AUDITED( false, StatusCode.ERROR, "当前商品未审核" ),
    GOODS_NOT_DELETE( false, StatusCode.ERROR, "当前商品处于未删除状态" ),
    GOODS_NOT_OFFLINE( false, StatusCode.ERROR, "当前商品必须处于下架状态才能删除" ),
    THE_GOODS_ARE_BEING_DELETED( false, StatusCode.ERROR, "当前商品处于删除状态" );
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
