package com.changgou.common.util;

import com.changgou.common.util.DecimalToBinary;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: Haotian
 * @Date: 2020/2/17 20:57
 * @Description: 十进制转二进制测试
 */
public class DecimalToBinaryTest {
    @Test
    public void test() {
        int x = 10;
        int y = 0;
        String convert1 = DecimalToBinary.convert( x );
        Assert.assertEquals( "1010", convert1 );
        String convert2 = DecimalToBinary.convert( y );
        Assert.assertEquals( "0", convert2 );
    }

    @Test
    public void conversionOfNumberSystems() {
        int i = 10;
        //转为二进制
        Assert.assertEquals( "1010", Integer.toBinaryString( i ) );
        //转为八进制
        System.out.println( i + " 的八进制为：" + Integer.toOctalString( i ) );
        //转为十六进制
        System.out.println( i + " 的十六进制为：" + Integer.toHexString( i ) );
        //转为三进制
        System.out.println( i + " 的三进制为：" + Integer.toString( i, 3 ) );

    }
}