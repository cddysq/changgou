package com.changgou.demo;

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
}