package com.changgou.seckill.feign;

import com.changgou.common.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Haotian
 * @Date: 2020/3/7 18:52
 * @Description: 秒杀服务下单对外接口
 */
@FeignClient(name = "seckill")
public interface SecKillOrderFeign {
    /**
     * 秒杀下单
     *
     * @param time 时间段
     * @param id   商品id
     * @return 提示信息
     */
    @GetMapping("/seckillorder/add")
    Result<Object> add(@RequestParam("time") String time, @RequestParam("id") Long id);
}