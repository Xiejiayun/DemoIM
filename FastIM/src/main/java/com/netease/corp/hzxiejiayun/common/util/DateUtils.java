package com.netease.corp.hzxiejiayun.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hzxiejiayun on 2016/6/21.
 * <p/>
 * 用来处理日期的工具类
 */
public class DateUtils {

    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        result = simpleDateFormat.format(date);
        return result;
    }

    public static Date parse(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            Date result = simpleDateFormat.parse(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long gap(Date one, Date two) {
        long oneMilSec = one.getTime();
        long twoMilSec = two.getTime();
        return Math.abs(oneMilSec-twoMilSec);
    }

    public static void main(String[] args) throws InterruptedException {
        String date = format(new Date());
        System.out.println(date);
        Date date1 = parse(date);
        System.out.println(date1);
        System.out.println(date1.getTime());
        Date date2 = new Date();
        System.out.println(date2.getTime());
        System.out.println(gap(date1, date2));
        date2 = new Date();
        System.out.println(date2.getTime());
        System.out.println(gap(date1, date2));
        Thread.sleep(100);
        date2 = new Date();
        System.out.println(date2.getTime());
        System.out.println(gap(date1, date2));
    }
}