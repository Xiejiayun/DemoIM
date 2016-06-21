package com.netease.corp.hzxiejiayun.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hzxiejiayun on 2016/6/21.
 */
public class NetworkUtils {

    /**
     * 获取当前主机的IP地址
     * @return
     */
    public static String getHost() {
        InetAddress inetAddress = null;
        String host = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            host = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
