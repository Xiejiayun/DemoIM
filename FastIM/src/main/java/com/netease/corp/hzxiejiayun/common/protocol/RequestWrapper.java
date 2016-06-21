package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.BaseModel;
import com.netease.corp.hzxiejiayun.common.model.LoginModel;
import com.netease.corp.hzxiejiayun.common.model.MessageModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 请求包装器，用来对请求进行协议封装
 */
public class RequestWrapper implements ProtocolWrapper {
    @Override
    public RequestModel wrap(BaseModel baseModel) {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocolType(baseModel.getProtocolType());
        requestModel.setHost(NetworkUtils.getHost());
        requestModel.setSenderid(baseModel.getSenderid());
        requestModel.setReceiverid(baseModel.getReceiverid());
        requestModel.setTimestamp(baseModel.getTimestamp());
        Map<String, String> extras = new HashMap<>();
        int protoType = requestModel.getProtocolType();
        switch (protoType) {
            case 0:
                break;//connection
            case 1:
                LoginModel loginModel = (LoginModel) baseModel;
                extras.put("username", loginModel.getUsername());
                extras.put("password", loginModel.getPassword());
                requestModel.setExtras(extras);
                break;//login
            case 2:
                break;//add friend
            case 3:
                MessageModel messageModel = (MessageModel) baseModel;
                extras.put("textMessage", messageModel.getTextMessage());
                requestModel.setExtras(extras);
                break;//send message
            default:
                break;
        }
        return requestModel;
    }
}