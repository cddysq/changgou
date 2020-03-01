package com.changgou.pay.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.pay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 21:46
 * @Description: 微信支付访问接口
 */
@RestController
@RequestMapping("/wxpay")
public class WxPayController {
    @Autowired
    private WxPayService wxPayService;

    /**
     * 本地支付
     *
     * @param orderId 订单id
     * @param money   支付金额
     * @return 提示信息
     */
    @GetMapping("/nativePay")
    public Result<Object> nativePay(@RequestParam("orderId") String orderId, @RequestParam("money") Integer money) {
        Map<String, String> resultMap = wxPayService.nativePay( orderId, money );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "" )
                .data( resultMap ).build();
    }
}