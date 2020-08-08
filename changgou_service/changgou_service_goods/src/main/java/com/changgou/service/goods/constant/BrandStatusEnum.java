package com.changgou.service.goods.constant;

import com.changgou.common.exception.ExceptionMessage;
import com.changgou.common.pojo.StatusCode;
import lombok.AllArgsConstructor;

/**
 * 品牌异常信息
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/8/8 15:47
 **/
@AllArgsConstructor
public enum BrandStatusEnum implements ExceptionMessage {
    BRAND_REPEAT( false, StatusCode.ERROR, "品牌已经存在" ),
    GOODS_VANISH( false, StatusCode.ERROR, "当前商品不存在" );
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
