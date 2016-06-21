package com.netease.corp.hzxiejiayun.server.processor;

import com.netease.corp.hzxiejiayun.common.model.*;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolWrapper;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.protocol.ResponseWrapper;
import com.netease.corp.hzxiejiayun.server.service.LoginService;

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
        return responseModel;
    }

    @Override
    public void service(RequestModel request, ResponseModel response) {

        int protocol = request.getProtocolType();
        if (protocol == 0) {

        } else if (protocol == 1) {
            LoginService loginService = new LoginService();
            loginService.service(request, response);
        } else if (protocol == 2) {

        } else if(protocol == 3) {
        }
        return;
    }
}
