package com.zss.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 *  时间类工具
 */

public class DateUtil {

    public static final String STANARD_FORMAT = "yyyy-MM-dd";

    public static final String STANARD_RORMAT_DETAIL = "HH:mm:ss";

    // 获取哪一天
    public static String whichDay(){
        Date date = new Date();
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANARD_FORMAT);
    }

    // 获取哪一个小时
    public static String whichHour(){
        Date date = new Date();
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANARD_RORMAT_DETAIL);
    }

    public static String change(String oldDate){
        String[] strs = oldDate.split("/");
        String newDate = strs[0] +"-"+ strs[1] +"-"+ strs[2];
        return newDate;
    }

    public static void main(String[] args) {
        String oldDate = "2019/06/07";
        String change = change(oldDate);
        System.out.println(change);
    }


}
