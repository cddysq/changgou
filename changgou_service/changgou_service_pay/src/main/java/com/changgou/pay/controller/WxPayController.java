package com.changgou.pay.controller;

import cn.hutool.core.convert.Convert;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.common.util.ConvertUtils;
import com.changgou.pay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 21:46
 * @Description: 微信支付访问接口
 */
@RestController
@RequestMapping("/wxpay")
@Slf4j
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

    /**
     * 支付回调
     */
    @RequestMapping("/notify")
    public void notifyLogic(HttpServletRequest request, HttpServletResponse response) {
        log.info( "支付成功回调。。。。" );
        //定义商户处理后返回给微信的参数
        String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        String xml = "";
        try {
            //输入流转字符串
            xml = ConvertUtils.convertToString( request.getInputStream() );
            //给微信一个结果通知
            response.getWriter().write( data );
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info( "微信返回通知内容{}", xml );
    }
}