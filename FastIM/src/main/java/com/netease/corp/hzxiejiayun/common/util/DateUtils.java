package com.netease.corp.hzxiejiayun.common.util;

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
}