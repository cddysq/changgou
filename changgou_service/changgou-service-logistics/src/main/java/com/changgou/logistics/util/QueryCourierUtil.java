package com.changgou.logistics.util;

import com.changgou.logistics.constant.LogisticsStatusEnum;
import com.changgou.logistics.exception.LogisticsException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        String appCode = "";
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
}