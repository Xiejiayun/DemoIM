package com.netease.corp.hzxiejiayun.server.processor;

import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.server.service.LoginService;

import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by hzxiejiayun on 2016/6/17.
 */
public class DefaultProcessor implements Processor {

    @Override
    public RequestModel createRequest() {
        return null;
    }

    @Override
    public ResponseModel createResponse() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setHost(NetworkUtils.getHost());
        responseModel.setTimestamp(DateUtils.format(new Date()));
        return responseModel;
    }

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {

        int protocol = request.getProtocolType();
        if (protocol == 0) {

        } else if (protocol == 1) {//登录
            LoginService loginService = new LoginService();
            loginService.service(request, response, socketChannel);
        } else if (protocol == 2) {

        } else if (protocol == 3) {//发送消息

        }
        return;
    }
}
