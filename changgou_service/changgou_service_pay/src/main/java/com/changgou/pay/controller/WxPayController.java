package com.changgou.pay.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.common.util.ConvertUtils;
import com.changgou.pay.config.RabbitMqConfig;
import com.changgou.pay.service.WxPayService;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    /**
     * 请求成功返回字符
     */
    private static final String SUCCESS = "SUCCESS";
    /**
     * 微信返回状态码字段名
     */
    private static final String RETURN_CODE = "return_code";
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

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
            //转换通知内容
            Map<String, String> map = WXPayUtil.xmlToMap( xml );
            if (SUCCESS.equals( map.get( RETURN_CODE ) )) {
                //基于微信发送通知内容，获取订单号查询订单
                Map<String, String> result = wxPayService.queryOrder( map.get( "out_trade_no" ) );
                log.info( "查询订单结果：{}", result );
                if (SUCCESS.equals( result.get( RETURN_CODE ) )) {
                    //将订单消息发送到mq
                    Map<String, String> dataMap = MapUtil.<String, String>builder()
                            //封装订单号
                            .put( "orderId", result.get( "out_trade_no" ) )
                            //微信支付订单号
                            .put( "transactionId", result.get( "transaction_id" ) ).build();
                    rabbitTemplate.convertAndSend( "", RabbitMqConfig.ORDER_PAY, JSON.toJSONString( dataMap ) );
                    //完成双向通信
                    rabbitTemplate.convertAndSend( "paynotify", "", result.get( "out_trade_no" ) );
                } else {
                    //打印错误信息
                    log.info( "从微信查询订单出错啦：{}", result.get( "err_code_des" ) );
                }
            } else {
                //打印错误信息
                log.info( "从微信查询订单出错啦：{}", map.get( "err_code_des" ) );
            }
            //给微信一个结果通知
            response.getWriter().write( data );
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info( "微信返回通知内容{}", xml );
    }

    /**
     * 基于微信查询订单
     *
     * @param orderId 订单号
     * @return 订单信息
     */
    @GetMapping("/query/{orderId}")
    public Result<Object> queryOrder(@PathVariable("orderId") String orderId) {
        Map<String, String> map = wxPayService.queryOrder( orderId );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询订单成功" )
                .data( map ).build();
    }

    /**
     * 基于微信关闭订单
     *
     * @param orderId 订单号
     * @return 订单信息
     */
    @PutMapping("/close/{orderId}")
    public Result<Object> closeOrder(@PathVariable("orderId") String orderId) {
        Map<String, String> map = wxPayService.queryOrder( orderId );
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "关闭订单成功" )
                .data( map ).build();
    }
}