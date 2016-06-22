package com.netease.corp.hzxiejiayun.client.hearbeat;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by hzxiejiayun on 2016/6/16.
 * <p/>
 * 这个线程是为了保持与服务器的连接，实现通信的长连接的操作
 */
public class HeartbeatThread extends Thread {

    SocketChannel socketChannel = null;
    String user = null;

    public HeartbeatThread(SocketChannel socketChannel, String user) {
        this.socketChannel = socketChannel;
        this.user = user;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //每隔10秒钟发送一个心跳包
                Thread.sleep(10000);
                RequestModel requestModel = new RequestModel();
                requestModel.setHost(NetworkUtils.getHost());
                requestModel.setTimestamp(DateUtils.format(new Date()));
                requestModel.setSenderid(user);
                ByteBuffer sendBuff = CommonWriter.setObject(requestModel);
                socketChannel.write(sendBuff);
            } catch (InterruptedException e) {
                System.out.println("发生中断异常");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("发生IO异常");
                System.exit(0);
            }
        }
    }
}
