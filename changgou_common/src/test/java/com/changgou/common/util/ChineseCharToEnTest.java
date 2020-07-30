package com.changgou.common.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 获取品牌首字母测试
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:31
 **/
public class ChineseCharToEnTest {
    @Test
    public void firstCapitalLetter() {
        ChineseCharToEn cte = ChineseCharToEn.getInstance();
        String brand = "虹猫";
        String firstCapitalLetter = cte.getFirstCapitalLetter( brand );
        Assert.assertEquals( "H", firstCapitalLetter );
    }
}
