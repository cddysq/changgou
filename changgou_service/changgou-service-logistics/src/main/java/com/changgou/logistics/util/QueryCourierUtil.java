package com.changgou.logistics.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.changgou.logistics.constant.LogisticsStatusEnum;
import com.changgou.logistics.exception.LogisticsException;
import com.changgou.logistics.pojo.ResultData;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/11 17:28
 * @Description: 快递查询工具类
 */
@Slf4j
public class QueryCourierUtil {

    /**
     * 查询快递信息
     *
     * @param courierNumber  快递单号
     * @param courierCompany 快递公司名
     * @return 订单物流信息
     */
    public static String queryCourier(String courierNumber, String courierCompany) {
        //请求地址  支持http 和 https 及 WEBSOCKET
        String host = "http://expressln.market.alicloudapi.com";
        //后缀
        String path = "/kdi";
        //AppCode  你自己的AppCode 在买家中心查看
        String appCode = "3647763d156c422fb4cf9350c624e672";
        //拼接请求链接
        String urlSend = host + path + "?no=" + courierNumber + "&type=" + courierCompany;
        String json = null;
        try {
            URL url = new URL( urlSend );
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            //格式Authorization:APPCODE (中间是英文空格)
            httpUrlConnection.setRequestProperty( "Authorization", "APPCODE " + appCode );
            int httpCode = httpUrlConnection.getResponseCode();
            log.info( "服务器响应状态码 200 正常；400 权限错误 ； 403 次数用完 = {}", httpCode );
            if (httpCode == 200) {
                json = read( httpUrlConnection.getInputStream() );
            } else {
                throw new LogisticsException( LogisticsStatusEnum.SYSTEM_ERROR );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 读取返回结果
     *
     * @param is 输入流
     * @return 结果集
     */
    private static String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String( line.getBytes(), "utf-8" );
            sb.append( line );
        }
        br.close();
        return sb.toString();
    }

    public static void main(String[] args) {
        /*
         * 4602195296853 [合肥市, 成都市]
         * 9861463666906 [东莞市, 成都市, 眉山市]
         */
        String queryCourier = queryCourier( "4602195296853", "" );
        ResultData resultData = JSON.parseObject( queryCourier, ResultData.class );
        //取到城市列表
        List<String> citys = resultData.getResult().getCitys();
        //第一个为货物发出城市名
        String startCity = CollUtil.getFirst( citys );
        //最后一个为货物到达城市名
        String lastCity = CollUtil.getLast( citys );
        //通过工具类拿到经纬度
        String[] lngAndLat = StrUtil.split( MapUtils.getLngAndLat( startCity ), "," );
        System.out.println( startCity );
        //转换后的数组第一个为经度，第二个为纬度
        System.out.println( Convert.toBigDecimal( lngAndLat[0] ) );
        System.out.println( Convert.toBigDecimal( lngAndLat[1] ) );
        System.out.println( lastCity );
    }
}