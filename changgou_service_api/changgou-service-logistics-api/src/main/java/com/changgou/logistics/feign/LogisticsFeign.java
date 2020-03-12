package com.changgou.logistics.feign;

import com.changgou.common.pojo.Result;
import com.changgou.logistics.pojo.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Haotian
 * @Date: 2020/3/12 14:11
 * @Description: 物流服务对外接口
 */
@FeignClient(name = "logistic")
public interface LogisticsFeign {
    /**
     * 根据订单号查询物流
     *
     * @param orderId        订单号
     * @param courierCompany 快递公司
     * @return 快递信息
     */
    @GetMapping("/logistics/findOrderLogistics")
    Result<ResultData> findOrderLogistics(@RequestParam("id") String orderId,
                                          @RequestParam(required = false, name = "courierCompany") String courierCompany);
}
