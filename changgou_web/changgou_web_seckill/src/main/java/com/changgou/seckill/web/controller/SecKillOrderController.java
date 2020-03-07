package com.changgou.seckill.web.controller;

import com.changgou.common.pojo.Result;
import com.changgou.seckill.feign.SecKillOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 21:30
 * @Description: 抢单接口
 */
@RestController
@RequestMapping("/wseckillorder")
public class SecKillOrderController {
    @Autowired
    private SecKillOrderFeign secKillOrderFeign;

    /**
     * 抢单
     *
     * @param time 当前时间段
     * @param id   商品id
     * @return 抢单信息
     */
    @GetMapping("/add")
    public Result<Object> add(@RequestParam("time") String time, @RequestParam("id") Long id) {
        return secKillOrderFeign.add( time, id );
    }
}