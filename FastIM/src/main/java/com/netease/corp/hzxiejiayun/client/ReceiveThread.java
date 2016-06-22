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
 * 用来接收外部的请求
 */
public class ReceiveThread extends Thread {
    ByteBuffer sendBuf = ByteBuffer.allocate(1024);
    String sendText = null;
    SocketChannel client;

    public ReceiveThread(SocketChannel socketChannel) {
        client = socketChannel;
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
                if (sendText.equals("q!"))
                    break;
                extras.put("message", sendText);
                requestModel.setExtras(extras);
                sendBuf = CommonWriter.setObject(requestModel);
                System.out.println(requestModel);
                sendBuf.flip();
                System.out.println();
                client.write(sendBuf);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}