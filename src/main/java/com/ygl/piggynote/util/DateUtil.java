package com.ygl.piggynote.util;

import java.text.*;
import java.util.Date;

/**
 * 时间工具类
 * Created by yanggavin on 14-7-18.
 */
public class DateUtil {

    /**
     * 日志格式化字符串形式
     */
    private static String dateFormatStr = "yyyy-MM-dd hh:mm:ss";


    /**
     * 格式化日期字符串
     * 形如：“2014-07-18 12:11:11”
     * @param date  日期实例
     * @return      字符串格式日期
     */
    public static String getDateString(Date date){

        DateFormat df = new SimpleDateFormat(dateFormatStr);
        return df.format(date);
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
