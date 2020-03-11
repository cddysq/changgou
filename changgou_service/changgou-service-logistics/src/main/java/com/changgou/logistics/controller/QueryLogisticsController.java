package com.changgou.logistics.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.logistics.util.QueryCourierUtil;
import com.changgou.logistics.pojo.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 19:18
 * @Description: 物流访问接口
 */
@RestController
@RequestMapping("/logistics")
public class QueryLogisticsController {
    /**
     * 根据订单号查询物流
     *
     * @param orderId        订单号
     * @param courierCompany 快递公司
     * @return 快递信息
     */
    @GetMapping("/findOrderLogistics")
    public Result<Object> findOrderLogistics(@RequestParam("id") String orderId,
                                             @RequestParam(required = false, name = "courierCompany") String courierCompany) {
        if (StrUtil.isBlank( orderId )) {
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "非法访问" ).build();
        }

        if (StrUtil.isBlank( courierCompany )) {
            courierCompany = "";
        }
        String json = QueryCourierUtil.queryCourier( orderId, courierCompany );
        ResultData resultData = JSON.parseObject( json, ResultData.class );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询物流信息成功" )
                .data( resultData ).build();
    }

}