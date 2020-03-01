package com.changgou.pay.feign;

import com.changgou.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 21:58
 * @Description: 支付对外接口
 */
@FeignClient(name = "pay")
public interface PayFeign {
    /**
     * 微信本地支付
     *
     * @param orderId 订单id
     * @param money   支付金额
     * @return 提示信息
     */
    @GetMapping("/wxpay/nativePay")
    Result<Object> nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money);
}