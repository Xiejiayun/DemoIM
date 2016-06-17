package com.netease.corp.hzxiejiayun.server.processor;

import com.netease.corp.hzxiejiayun.server.model.RequestModel;
import com.netease.corp.hzxiejiayun.server.model.ResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/17.
 */
public class DefaultProcessor implements Processor{
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
        
    }
}
