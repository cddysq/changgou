package com.changgou.seckill.controller;

import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.seckill.pojo.SeckillGoods;
import com.changgou.seckill.service.SecKillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 20:58
 * @Description: 秒杀访问接口
 */
@RestController
@RequestMapping("/secKillGoods")
public class SecKillGoodsController {
    @Autowired
    private SecKillGoodsService secKillGoodsService;

    /**
     * 查询当前时间段商品秒杀列表
     *
     * @param time 当前时间段
     * @return 商品列表信息
     */
    @GetMapping("/list")
    public Result<List<SeckillGoods>> list(@RequestParam("time") String time) {
        List<SeckillGoods> list = secKillGoodsService.list( time );
        return Result.<List<SeckillGoods>>builder()
                .flag( true )
                .code( StatusCode.OK )
                .data( list )
                .message( "查询当前时间段秒杀商品信息成功" ).build();
    }
}