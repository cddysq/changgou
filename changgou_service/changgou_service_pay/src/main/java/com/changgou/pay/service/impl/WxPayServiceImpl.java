package com.changgou.pay.service.impl;

import cn.hutool.core.map.MapUtil;
import com.changgou.pay.service.WxPayService;
import com.github.wxpay.sdk.WXPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 21:25
 * @Description: 微信支付实现
 */
@Service
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    private WXPay wxPay;

    @Override
    public Map<String, String> nativePay(String orderId, Integer money) {
        //金额转换为分
        BigDecimal payMoney = new BigDecimal( "0.01" );
        BigDecimal fen = payMoney.multiply( new BigDecimal( "100" ) );
        fen = fen.setScale( 0, BigDecimal.ROUND_UP );
        //1.封装请求参数
        Map<String, String> map = MapUtil.<String, String>builder()
                //商品描述
                .put( "body", "畅购" )
                //商户订单号
                .put( "out_trade_no", orderId )
                //标价金额
                .put( "total_fee", String.valueOf( fen ) )
                //终端IP
                .put( "spbill_create_ip", "127.0.0.1" )
                //通知地址
                .put( "notify_url", "https://www.yileaf.com" )
                .put( "trade_type", "NATIVE" )
                .build();
        //2.基于wxPay完成统一接口的调用，并获取返回值
        try {
            return wxPay.unifiedOrder( map );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
