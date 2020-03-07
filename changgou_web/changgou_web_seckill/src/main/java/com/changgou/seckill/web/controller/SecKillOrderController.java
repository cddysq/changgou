package com.changgou.seckill.web.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.seckill.feign.SecKillOrderFeign;
import com.changgou.seckill.web.aspect.AccessLimit;
import com.changgou.seckill.web.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 抢单
     *
     * @param time   当前时间段
     * @param id     商品id
     * @param random 接口访问随机字符
     * @return 抢单信息
     */
    @GetMapping("/add")
    @AccessLimit
    public Result<Object> add(@RequestParam("time") String time, @RequestParam("id") Long id, @RequestParam("random") String random, HttpServletRequest request) {
        String jti = CookieUtil.readCookie( request, "uid" ).get( "uid" );
        String redisRandomCode = (String) redisTemplate.opsForValue().get( "randomCode_" + jti );
        //进行接口访问，随机字符校验
        if (StrUtil.isEmpty( redisRandomCode ) || !redisRandomCode.equals( random )) {
            return Result.builder()
                    .flag( false )
                    .code( StatusCode.ERROR )
                    .message( "下单失败" ).build();
        }
        return secKillOrderFeign.add( time, id );
    }

    /**
     * 生成访问接口随机数
     *
     * @return 接口请求随机数
     */
    @GetMapping("/getToken")
    @ResponseBody
    public String getToken(HttpServletRequest request) {
        String uuid = IdUtil.simpleUUID();
        String jti = CookieUtil.readCookie( request, "uid" ).get( "uid" );
        redisTemplate.opsForValue().set( "randomCode_" + jti, uuid, 5, TimeUnit.SECONDS );
        return uuid;
    }
}