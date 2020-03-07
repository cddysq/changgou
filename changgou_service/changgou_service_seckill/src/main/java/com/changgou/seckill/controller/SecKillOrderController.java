package com.changgou.seckill.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.seckill.config.TokenDecode;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/6 15:22
 * @Description: 秒杀下单接口
 */
@RestController
@RequestMapping("/seckillorder")
public class SecKillOrderController {
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private SecKillOrderService secKillOrderService;

    /**
     * 秒杀下单
     *
     * @param time 时间段
     * @param id   商品id
     * @return 提示信息
     */
    @GetMapping("/add")
    public Result<Object> add(@RequestParam("time") String time, @RequestParam("id") Long id) {
        //1.获取当前登录用户名
        String username = tokenDecode.getUserInfo().get( "username" );
        //2.秒杀下单
        boolean result = secKillOrderService.add( id, time, username );
        //3.回显结果
        if (result) {
            //下单成功
            return Result.builder()
                    .flag( true )
                    .code( StatusCode.OK )
                    .message( "下单成功" ).build();
        } else {
            //下单失败
            return Result.builder()
                    .flag( false )
                    .code( StatusCode.ERROR )
                    .message( "下单失败" ).build();
        }
    }
}