package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.common.util.DateUtils;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/22.
 * <p/>
 * 这边用来存储一个套接字缓存
 */
public class CachedSocket implements Runnable{

    public static Map<String, SocketChannel> cachedSockets = new HashMap<>();
    public static Map<String, SelectionKey> cachedKeys = new HashMap<>();

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

    public static String getUserFromSelectionKey(SelectionKey selectionKey) {
        for (String user : cachedKeys.keySet()) {
            SelectionKey key = cachedKeys.get(user);
            if (key.equals(selectionKey))
                return user;
        }
        return null;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            Date current = new Date();
            for (String user : cachedSockets.keySet()) {
                String heartbeat = CachedHeartBeat.heartBeatData.get(user);
                if (heartbeat == null)
                    continue;
                Date heartBeatDate = DateUtils.parse(heartbeat);
                long gap = DateUtils.gap(current, heartBeatDate);
                double minutes = (double)gap/60000;
                if (minutes > 1) {
                    //如果用户超时1分钟，则删除指定用户
                    CachedSocket.cachedSockets.remove(user);
                    CachedSocket.cachedKeys.remove(user);
                    System.out.println("心跳包1分钟没有响应，关闭用户" + user + "的通道");
                    break;
                }
            }
        }
    }
}