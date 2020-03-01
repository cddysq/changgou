package com.changgou.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Haotian
 * @Date: 2020/3/1 23:19
 * @Description: 转换工具类
 **/
public class ConvertUtils {

    /**
     * 输入流转换为xml字符串
     *
     * @param inputStream 输入流
     * @return 字符串
     */
    public static String convertToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read( buffer )) != -1) {
            outSteam.write( buffer, 0, len );
        }
        outSteam.close();
        inputStream.close();
        return new String( outSteam.toByteArray(), "utf-8" );
    }
}