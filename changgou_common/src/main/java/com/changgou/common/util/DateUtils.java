package com.changgou.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    /**
     * 从yyyy-MM-dd HH:mm格式转成yyyyMMddHH格式
     */
    public static String formatStr(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
        try {
            Date date = simpleDateFormat.parse( dateStr );
            simpleDateFormat = new SimpleDateFormat( "yyyyMMddHH" );
            return simpleDateFormat.format( date );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期的凌晨
     */
    public static Date toDayStartHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );
        return calendar.getTime();
    }

    /**
     * 时间增加N分钟
     */
    public static Date addDateMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        // 24小时制
        calendar.add( Calendar.MINUTE, minutes );
        date = calendar.getTime();
        return date;
    }

    /**
     * 时间递增N小时
     */
    public static Date addDateHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        // 24小时制
        calendar.add( Calendar.HOUR, hour );
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取时间菜单
     */
    public static List<Date> getDateMenus() {

        // 定义一个List<Date>集合，存储所有时间段
        List<Date> dates = new ArrayList<>();

        // 凌晨
        Date date = toDayStartHour( new Date() );
        // 循环12次
        for (int i = 0; i < 12; i++) {
            // 每次递增2小时,将每次递增的时间存入到List<Date>集合中
            dates.add( addDateHour( date, i * 2 ) );
        }

        // 判断当前时间属于哪个时间范围
        Date now = new Date();
        for (Date cdate : dates) {
            // 开始时间<=当前时间<开始时间+2小时
            if (cdate.getTime() <= now.getTime() && now.getTime() < addDateHour( cdate, 2 ).getTime()) {
                now = cdate;
                break;
            }
        }

        // 当前需要显示的时间菜单
        List<Date> dateMenus = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            dateMenus.add( addDateHour( now, i * 2 ) );
        }
        return dateMenus;
    }

    /**
     * 时间转成yyyyMMddHH
     */
    public static String date2Str(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyyMMddHH" );
        return simpleDateFormat.format( date );
    }
}