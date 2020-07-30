package com.changgou.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 转换工具类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:26
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
        return new String( outSteam.toByteArray(), StandardCharsets.UTF_8 );
    }
}