package com.changgou.seckill.web.controller;

import cn.hutool.core.date.DateUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.util.DateUtils;
import com.changgou.seckill.feign.SecKillGoodsFeign;
import com.changgou.seckill.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/5 19:24
 * @Description: 秒杀渲染接口
 */
@Controller
@RequestMapping("/wseckillgoods")
public class SecKillGoodsController {
    /**
     * redis 中秒杀商品库存 key 前缀
     */
    private static final String SEC_KILL_GOODS_STOCK_COUNT_KEY = "sec_kill_goods_stock_count_key";

    @Autowired
    private SecKillGoodsFeign secKillGoodsFeign;

    /**
     * 跳转秒杀页面
     *
     * @return 秒杀页面
     */
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "seckill-index";
    }

    /**
     * 获取秒杀时间段集合信息
     *
     * @return 数据集合
     */
    @GetMapping("/timeMenus")
    @ResponseBody
    public List<String> timeMenus() {
        //获取当前时间段相关信息集合
        List<Date> dateMenus = DateUtils.getDateMenus();
        List<String> result = new ArrayList<>();
        for (Date dateMenu : dateMenus) {
            String dateTime = DateUtil.formatDateTime( dateMenu );
            result.add( dateTime );
        }
        return result;
    }

    /**
     * 查询秒杀时间段商品列表
     *
     * @param time 当前时间段
     * @return 商品列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<SeckillGoods>> list(String time) {
        return secKillGoodsFeign.list( DateUtils.formatStr( time ) );
    }
}