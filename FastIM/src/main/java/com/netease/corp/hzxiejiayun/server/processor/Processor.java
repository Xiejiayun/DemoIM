package com.netease.corp.hzxiejiayun.server.processor;

import com.netease.corp.hzxiejiayun.server.model.RequestModel;
import com.netease.corp.hzxiejiayun.server.model.ResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/17.
 */
public interface Processor {

    RequestModel createRequest();

    ResponseModel createResponse();

    void service(RequestModel request, ResponseModel response);

}
