package com.netease.corp.hzxiejiayun.common.util;

/**
 * Created by hzxiejiayun on 2016/6/17.
 */
public class StringUtils {

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        char tmp[] = str.toCharArray();
        for (int i=0, j=tmp.length-1; i<j; ) {
            if (tmp[i] == ' ') {
                i++;
                continue;
            }
            if (tmp[j] == ' ') {
                j--;
                continue;
            }
            return str.substring(i, j+1);
        }
        return "";
    }

    public static void main(String[] args) {
        String s = null;
        System.out.println(StringUtils.trim(s));
        s = "";
        System.out.println(StringUtils.trim(s));
        s = " c d    ";
        System.out.println(StringUtils.trim(s) + StringUtils.trim(s).length());
        s = "  a b c   ";
        System.out.println(StringUtils.trim(s) + StringUtils.trim(s).length());
    }
}