package com.changgou.logistics.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.changgou.logistics.feign.LogisticsFeign;
import com.changgou.logistics.pojo.ResultData;
import com.changgou.logistics.web.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/3/12 13:56
 * @Description: 展示物流信息
 */
@Controller
@RequestMapping("/wlogistics")
public class DisplayLogisticsInformationController {
    @Autowired
    private LogisticsFeign logisticsFeign;

    /**
     * 前往物流信息页面
     *
     * @return 物流信息展示界面
     */
    @RequestMapping("/toLogisticsInfo")
    public String toLogisticsInfo(@RequestParam("id") String orderId, Model model) {
        ResultData resultData = logisticsFeign.findOrderLogistics( orderId, "" ).getData();
        //得到城市列表
        List<String> citys = resultData.getResult().getCitys();
        //得到出发城市经纬度
        String[] startLngAndLat = StrUtil.split( MapUtils.getLngAndLat( CollUtil.getFirst( citys ) ), "," );
        //得到结束城市经纬度
        String[] endLngAndLat = StrUtil.split( MapUtils.getLngAndLat( CollUtil.getLast( citys ) ), "," );
        Map<String, String> dataMap = MapUtil.<String, String>builder()
                .put( "order", "当前运送快递单号：" + orderId )
                .put( "startLongitude", Convert.toStr( startLngAndLat[0] ) )
                .put( "startLatitude", Convert.toStr( startLngAndLat[1] ) )
                .put( "endLongitude", Convert.toStr( endLngAndLat[0] ) )
                .put( "endLatitude", Convert.toStr( endLngAndLat[1] ) ).build();
        model.addAllAttributes( dataMap );
        return "queryLogistics";
    }
}
