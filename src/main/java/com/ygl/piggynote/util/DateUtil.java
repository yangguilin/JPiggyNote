package com.ygl.piggynote.util;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 * Created by yanggavin on 14-7-18.
 */
public class DateUtil {

    /**
     * 日期格式化字符串
     */
    private static String dateFormatStr = "yyyy-MM-dd hh:mm:ss";
    /**
     * 短日期格式化字符串
     */
    private static String shortDateFormatStr = "yyyy-MM-dd";
    /**
     * 月份格式化字符串
     */
    private static String monthDateFormatStr = "yyyy年MM月";



    /**
     * 格式化日期字符串
     * 形如：“2014-07-18 12:11:11”
     * @param date  日期实例
     * @return      日期字符串
     */
    public static String getDateStr(Date date){

        DateFormat df = new SimpleDateFormat(dateFormatStr);
        return df.format(date);
    }

    /**
     * 格式化日期字符串
     * 形如：“2014-08-19”
     * @return 日期字符串
     */
    public static String getShortDateStr(Date date){

        DateFormat df = new SimpleDateFormat(shortDateFormatStr);
        return df.format(date);
    }

    /**
     * 格式化月份字符串，用于统计数据
     * @param date  日期
     * @return  月份字符串
     */
    public static String getMonthStr(Date date){

        DateFormat df = new SimpleDateFormat(monthDateFormatStr);
        return df.format(date);
    }

    /**
     * 获取以今天为起点，相应天数差值计算后的日期
     * @param index 天数差值
     * @return  短日期字符串
     */
    public static Date getDateByIndex(int index){

        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, index);
        return cal.getTime();
    }

    /**
     * 获取相对于今天计算后的日期字符串，时间默认为"00:00:00"
     * @param year  年
     * @param month 月
     * @param day   日
     * @return  运算后的日期字符串
     */
    public static String getDateStr(int year, int month, int day){

        // 日期相对运算
        Date dt = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR, year);
        rightNow.add(Calendar.MONTH, month);
        rightNow.add(Calendar.DAY_OF_YEAR, day);
        Date newDate = rightNow.getTime();

        // 转为字符串格式
        SimpleDateFormat sdf = new SimpleDateFormat(shortDateFormatStr);
        return sdf.format(newDate);
    }

    /**
     * 按照统一的格式将字符串解析为日期实例
     * 形如：“2014-07-18 12:11:11”
     * @param dateStr   待解析日期字符串
     * @return          日期实例
     * @throws ParseException   解析日期字符串异常
     */
    public static Date getDateFromStr(String dateStr) throws ParseException {

        DateFormat df = new SimpleDateFormat(dateFormatStr);
        return df.parse(dateStr);
    }


}
