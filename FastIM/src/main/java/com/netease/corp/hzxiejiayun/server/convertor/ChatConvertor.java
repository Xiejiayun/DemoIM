package com.netease.corp.hzxiejiayun.server.convertor;

import com.netease.corp.hzxiejiayun.common.model.ChatModel;
import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;

/**
 * Created by hzxiejiayun on 2016/6/23.
 */
public class ChatConvertor {

    public static ChatModel doTOModel(ChatDO chatDO) {
        ChatModel model = new ChatModel();
        model.setChatid(chatDO.getChatid());
        model.setChattime(chatDO.getChattime());
        model.setSender(chatDO.getSender());
        model.setReceiver(chatDO.getReceiver());
        model.setMessage(chatDO.getMessage());
        model.setStatus(chatDO.getStatus());
        return model;
    }
}
