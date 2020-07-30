package com.changgou.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 十进制转二进制
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:30
 **/
public class DecimalToBinary {
    public static String convert(Integer number) {
        List<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        while (number / 2 != 0) {
            list.add( number % 2 );
            number = number / 2;
        }
        list.add( number % 2 );
        for (Integer i : list) {
            sb.append( i );
        }
        sb.reverse();

        return sb.toString();
    }
}