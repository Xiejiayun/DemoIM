package com.netease.corp.hzxiejiayun.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/23.
 */
public class CachedHeartBeat {

    public static Map<String, String> heartBeatData = new HashMap<>();

    public static void put(String user, String date) {
        heartBeatData.put(user, date);
        System.out.println("服务端接收到的心跳包：来自用户"+user+" 在"+ date+" 时刻的心跳包");
    }
}
