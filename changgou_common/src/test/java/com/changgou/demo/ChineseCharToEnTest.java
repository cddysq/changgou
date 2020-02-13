package com.changgou.demo;

import com.changgou.common.util.ChineseCharToEn;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: Haotian
 * @Date: 2020/2/13 20:53
 * @Description: 获取品牌首字母测试
 */
public class ChineseCharToEnTest {
    @Test
    public void firstCapitalLetter() {
        ChineseCharToEn cte = ChineseCharToEn.getInstance();
        String brand = "虹猫";
        String firstCapitalLetter = cte.getFirstCapitalLetter( brand );
        Assert.assertEquals( "H", firstCapitalLetter );
    }
}
