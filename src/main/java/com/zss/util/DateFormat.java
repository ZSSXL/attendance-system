package com.zss.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 *  时间类工具
 */

public class DateFormat {

    public static final String STANARD_FORMAT = "yyyy-MM-dd";

    public static final String STANARD_RORMAT_DETAIL = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当天日日期，并转换为yyyy-mm-dd的格式
     * @return
     */


    public static String whichDay(){
        Date date = new Date();
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANARD_FORMAT);
    }

    public static String whichHour(){
        Date date = new Date();
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANARD_RORMAT_DETAIL);
    }

    public static void main(String[] args) {
        String date = whichDay();
        System.out.println(date);
        String s = whichHour();
        System.out.println(s);
    }

}
