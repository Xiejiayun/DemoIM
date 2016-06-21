package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.model.MessageModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;

import java.nio.channels.SocketChannel;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class MessageService implements Service {
    private ProtocolParser protocolParser = new RequestParser();

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {
        MessageModel messageModel = (MessageModel) protocolParser.parse(request);

    }


}