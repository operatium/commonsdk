package com.yyx.commonsdk.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/12.
 */

public class TimeUtil {
    /**
     *     时间戳转换成日期格式字符串
     *     @param seconds 精确到秒的字符串
     *     @param format
     *     @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    /**
     *     时间戳转换成日期格式字符串
     *     @param time 精确到秒的字符串
     *     @param format
     *     @return
     */
    public static String timeStamp2Date(long time, String format) {
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }
}
