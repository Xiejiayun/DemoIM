package com.netease.corp.hzxiejiayun.server.servicehandler;

import com.netease.corp.hzxiejiayun.server.model.MessageModel;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public interface Handler {

    void handle(MessageModel messageModel);
}