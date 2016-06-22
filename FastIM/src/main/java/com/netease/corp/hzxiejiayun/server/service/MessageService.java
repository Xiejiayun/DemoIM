package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.MessageModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.server.CachedSocket;
import com.netease.corp.hzxiejiayun.server.dao.RelationDao;
import com.netease.corp.hzxiejiayun.server.dao.UserDao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class MessageService implements Service {
    private ProtocolParser protocolParser = new RequestParser();
    private UserDao userDao = UserDao.UserDaoHandler.getUserDao();
    private RelationDao relationDao = RelationDao.RelationDaoHandler.getRelationDao();

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {
        MessageModel messageModel = (MessageModel) protocolParser.parse(request);
        String receiver = messageModel.getReceiverid();
        boolean isReceiverOnline = CachedSocket.cachedSockets.containsKey(receiver);
        if (isReceiverOnline) {
            //将信息转发给在线用户
            ByteBuffer sendBuff;
            sendBuff = CommonWriter.setObject(request);
            SocketChannel receiverChannel = CachedSocket.cachedSockets.get(receiver);
            try {
                receiverChannel.write(sendBuff);
                System.out.println(receiverChannel + request.toString());
            } catch (IOException e) {
                System.out.println("消息转发失败");
            }
        } else {
            //将数据写入数据库，等用户登录的时候自己获取
        }
    }
}