package com.changgou.web.order.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.changgou.order.pojo.OrderInfoCount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Haotian
 * @Date: 2020/3/10 12:29
 * @Description: 图表数据测试
 */
public class EChartsTest {
    @Test
    public void test() {
        //统计信息集合
        List<OrderInfoCount> infoData = new ArrayList<>();
        OrderInfoCount count1 = OrderInfoCount.builder().name( "待付款订单" ).value( 335 ).build();
        OrderInfoCount count2 = OrderInfoCount.builder().name( "待发货订单" ).value( 310 ).build();
        OrderInfoCount count3 = OrderInfoCount.builder().name( "已发货订单" ).value( 234 ).build();
        OrderInfoCount count4 = OrderInfoCount.builder().name( "已完成订单" ).value( 135 ).build();
        OrderInfoCount count5 = OrderInfoCount.builder().name( "已关闭订单" ).value( 1548 ).build();
        infoData.add( count1 );
        infoData.add( count2 );
        infoData.add( count3 );
        infoData.add( count4 );
        infoData.add( count5 );
        //统计名集合
        List<String> orderInfoName = infoData.stream().map( OrderInfoCount::getName ).collect( Collectors.toList() );
        //返回前端数据
        Map<String, Object> resultData = MapUtil.<String, Object>builder()
                .put( "infoName", orderInfoName )
                .put( "infoData", infoData ).build();
        String string = JSON.toJSONString( resultData, SerializerFeature.PrettyFormat );
        System.out.println( string );
    }
}