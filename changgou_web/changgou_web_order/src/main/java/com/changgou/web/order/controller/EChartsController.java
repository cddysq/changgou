package com.changgou.web.order.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.OrderInfoCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Haotian
 * @Date: 2020/3/9 20:53
 * @Description: 数据可视化
 */
@Controller
@RequestMapping("/echarts")
public class EChartsController {
    @Autowired
    private OrderFeign orderFeign;

    /**
     * 跳转订单统计页面
     *
     * @return 统计分析订单
     */
    @RequestMapping("/toOrder")
    public String orderStatistics() {
        return "orderstatistics";
    }

    /**
     * 获取统计信息
     *
     * @param startTime 开始时间戳
     * @param endTime   结束时间戳
     * @return 统计信息集合
     */
    @GetMapping("/orderInfoCount")
    @ResponseBody
    public Result<Object> orderInfoCount(@RequestParam(required = false, name = "startTime") String startTime,
                                         @RequestParam(required = false, name = "endTime") String endTime) {

        List<OrderInfoCount> infoData = orderFeign.getOrderInfoData( startTime, endTime );
        //返回前端数据
        Map<String, Object> resultData = MapUtil.<String, Object>builder()
                .put( "infoName",
                        infoData.stream()
                                .map( OrderInfoCount::getName )
                                //统计名集合
                                .collect( Collectors.toList() )
                )
                .put( "infoData", infoData ).build();
        return Result.builder()
                .flag( true )
                .code( StatusCode.OK )
                .message( "查询统计信息成功" )
                .data( resultData ).build();
    }

    /**
     * 导出订单统计数据Excel
     *
     * @return 是否导出成功
     */
    @PostMapping("/exportOderInfoCount")
    @ResponseBody
    public Result<Object> exportOderInfoCount(@RequestBody List<OrderInfoCount> infoData, HttpServletResponse response) {
        //获得Excel模板文件绝对路径
        ClassPathResource resource = new ClassPathResource( "static" + File.separator + "excel" + File.separator + "report_template.xlsx" );
        //读取excel设置数据
        ExcelReader excel = null;
        try {
            excel = ExcelUtil.getReader( resource.getInputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReader setSheet = Objects.requireNonNull( excel ).setSheet( 0 );
        //定义初始行号
        int row = 3;
        for (OrderInfoCount info : infoData) {
            //填充数据
            setSheet.getCell( 2, row ).setCellValue( info.getValue() );
            row++;
        }
        //创建excel输出流
        ExcelWriter writer = excel.getWriter();
        //代表的是Excel文件类型
        response.setContentType( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8" );
        //指定以附件形式进行下载
        response.setHeader( "Content-Disposition", "attachment;filename=report.xlsx" );
        //创建页面输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            writer.flush( outputStream, true );
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "系统异常，请稍后重试" ).build();
        } finally {
            // 关闭writer，释放内存
            Objects.requireNonNull( writer ).close();
            IoUtil.close( outputStream );
        }
    }
}