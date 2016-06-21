package com.netease.corp.hzxiejiayun.server.processor;

import com.netease.corp.hzxiejiayun.common.model.ConnectionModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolWrapper;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.protocol.ResponseWrapper;

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
        return null;
    }

    @Override
    public void service(RequestModel request, ResponseModel response) {
        ProtocolParser protocolParser = new RequestParser();
        int protocol = request.getProtocolType();
        if (protocol == 0) {
            ConnectionModel connectionModel = (ConnectionModel) protocolParser.parse(request);
        }

        ProtocolWrapper protocolWrapper = (ProtocolWrapper) new ResponseWrapper();
//        response = protocolWrapper.wrap(new BaseModel());
        return;
    }
}
