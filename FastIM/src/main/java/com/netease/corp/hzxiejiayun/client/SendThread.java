package com.netease.corp.hzxiejiayun.client;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 发送消息的线程
 */
public class SendThread extends Thread {
    ByteBuffer sendBuf = ByteBuffer.allocate(1024);
    String sendText = null;
    SocketChannel socketChannel;

    public SendThread(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        RequestModel requestModel = new RequestModel();
        Map<String, String> extras = new HashMap<>();
        while (true) {
            try {
                sendBuf.clear();
                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(input);
                sendText = br.readLine();
                extras.put("message", sendText);
                requestModel.setExtras(extras);
                sendBuf = CommonWriter.setObject(requestModel);
                sendBuf.flip();
                socketChannel.write(sendBuf);
                System.out.println(sendBuf);
                System.out.println(socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}