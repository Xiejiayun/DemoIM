package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.BaseModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

/**
 * Created by hzxiejiayun on 2016/6/20.
 */
public class ResponseWrapper implements ProtocolWrapper {
    @Override
    public ResponseModel wrap(BaseModel baseModel) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setProtocolType(baseModel.getProtocolType());
        responseModel.setHost(NetworkUtils.getHost());
        responseModel.setSenderid(baseModel.getSenderid());
        responseModel.setReceiverid(baseModel.getReceiverid());
        responseModel.setTimestamp(baseModel.getTimestamp());
        responseModel.setResponseCode(baseModel.getResponseCode());
        responseModel.setResponseContent(baseModel.getResponseContent());
        return responseModel;
    }
}
