package com.changgou.seckill.feign;

import com.changgou.common.pojo.Result;
import com.changgou.seckill.pojo.SeckillGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 21:06
 * @Description: 秒杀服务对外接口
 */
@FeignClient(name = "seckill")
public interface SecKillGoodsFeign {
    /**
     * 查询当前时间段商品秒杀列表
     *
     * @param time 当前时间段
     * @return 商品列表信息
     */
    @GetMapping("/secKillGoods/list")
    Result<List<SeckillGoods>> list(@RequestParam("time") String time);
}