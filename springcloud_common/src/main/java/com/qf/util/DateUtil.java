package com.qf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    /**
     * 获得当前实现的下n个小时的Date对象（整点）
     * @param n
     * @return
     */
    public static Date getNextNDate(int n){

        //获得日历对象
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.HOUR_OF_DAY, n);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //获得日历对应的时间
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 返回固定格式的字符串，返回时间字符串
     * @param time
     * @param pattern
     * @return
     */
    public static String dateToString(Date time,String pattern){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
        return simpleDateFormat.format(time);
    }
}
