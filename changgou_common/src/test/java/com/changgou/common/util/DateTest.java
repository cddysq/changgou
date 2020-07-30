package com.changgou.common.util;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

/**
 * 计算两个时相差天数
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/7/30 15:31
 **/
public class DateTest {
    @Test
    public void timeDifference() {
        long day = DateUtil.betweenDay( DateUtil.date(), DateUtil.parseDate( "2020-02-10" ), true );
        System.out.println( "相差" + day + "天" );
    }
}
