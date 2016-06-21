package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;

import java.nio.channels.SocketChannel;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public interface Service {

    void service(RequestModel request, ResponseModel response, SocketChannel socketChannel);
}