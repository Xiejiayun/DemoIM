package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.MessageModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;
import com.netease.corp.hzxiejiayun.server.CachedSocket;
import com.netease.corp.hzxiejiayun.server.dao.ChatDao;
import com.netease.corp.hzxiejiayun.server.dao.RelationDao;
import com.netease.corp.hzxiejiayun.server.dao.UserDao;
import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class MessageService implements Service {
    private ProtocolParser protocolParser = new RequestParser();
    private ChatDao chatDao = ChatDao.ChatDaoHandler.getChatDao();

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {
        MessageModel messageModel = (MessageModel) protocolParser.parse(request);
        String receiver = messageModel.getReceiverid();
        boolean isReceiverOnline = CachedSocket.cachedSockets.containsKey(receiver);
        ChatDO chatDO = new ChatDO();
        String senderid = request.getSenderid();
        String receiverid = request.getReceiverid();
        String chattime = request.getTimestamp();
        String message = request.getExtras().get("message");
        chatDO.setSender(senderid);
        chatDO.setReceiver(receiverid);
        chatDO.setChattime(chattime);
        chatDO.setMessage(message);
        if (isReceiverOnline) {
            //将信息转发给在线用户
            ByteBuffer sendBuff;
            sendBuff = CommonWriter.setObject(request);
            SocketChannel receiverChannel = CachedSocket.cachedSockets.get(receiver);
            try {
                receiverChannel.write(sendBuff);
                System.out.println(receiverChannel + request.toString());
                chatDO.setStatus("y");
            } catch (IOException e) {
                System.out.println("消息转发失败");
                chatDO.setStatus("n");
            }
        } else {
            //将数据写入数据库，等用户登录的时候自己获取
            chatDO.setStatus("n");
        }
        chatDao.add(chatDO);
    }
}