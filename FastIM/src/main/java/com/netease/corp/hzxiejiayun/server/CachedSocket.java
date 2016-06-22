package com.netease.corp.hzxiejiayun.server;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/22.
 * <p/>
 * 这边用来存储一个套接字缓存
 */
public class CachedSocket {

    public static Map<String, SocketChannel> cachedSockets = new HashMap<>();

    public static boolean put(String user, SocketChannel socketChannel) {
        if (cachedSockets == null)
            cachedSockets = new HashMap<>();
        try {
            cachedSockets.put(user, socketChannel);
        } catch (Exception e) {
            System.out.println("向缓存中添加套接字通道失败");
            return false;
        }
        return true;
    }

    public static boolean remove(String user) {
        if (cachedSockets == null)
            cachedSockets = new HashMap<>();
        try {
            cachedSockets.remove(user);
        } catch (Exception e) {
            System.out.println("删除特定用户的套接字缓存失败");
            return false;
        }
        return true;
    }
}