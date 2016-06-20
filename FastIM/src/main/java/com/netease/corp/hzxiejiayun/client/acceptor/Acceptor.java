package com.netease.corp.hzxiejiayun.client.acceptor;

import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by hzxiejiayun on 2016/6/16.
 * <p/>
 * Acceptor的主要作用就是监听通信的端口
 */
public class Acceptor extends Thread {

    private ServerSocketChannel serverSock = null;

    private ServerSocket serverSocket = null;

    public static boolean running = false;

    @Override
    public void run() {
        while (running) {

        }
    }

}
