package com.changgou.common.util;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

/**
 * @Author: Haotian
 * @Date: 2020/2/13 19:39
 * @Description: 计算两个时相差天数
 */
public class DateTest {
    @Test
    public void timeDifference() {
        long day = DateUtil.betweenDay( DateUtil.date(), DateUtil.parseDate( "2020-02-10" ), true );
        System.out.println( "相差" + day + "天" );
    }
}
