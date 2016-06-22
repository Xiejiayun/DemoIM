package com.netease.corp.hzxiejiayun.client;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by hzxiejiayun on 2016/6/20.
 */
public class SendThread extends Thread {
    SocketChannel client = null;
    RequestModel requestModel = null;

    public SendThread(SocketChannel socketChannel, RequestModel requestModel) {
        client = socketChannel;
        this.requestModel = requestModel;
        if (requestModel.getExtras() == null)
            requestModel.setExtras(new HashMap<String, String>());
    }

    @Override
    public void run() {
        ByteBuffer sendBuf = ByteBuffer.allocate(1024);
        String sendText = null;
        while (true) {
            try {
                System.out.println("||--Please input the message--||");
                sendBuf.clear();
                InputStreamReader input = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(input);
                sendText = br.readLine();
                if (sendText.equals("q!")) {
                    System.out.println("Bye!");
                    break;
                }
                requestModel.getExtras().put("message", sendText);
                requestModel.setTimestamp(DateUtils.format(new Date()));
                sendBuf = CommonWriter.setObject(requestModel);
                System.out.println(requestModel);
                sendBuf.flip();
                client.write(sendBuf);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}