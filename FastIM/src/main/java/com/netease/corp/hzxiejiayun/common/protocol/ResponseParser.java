package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.*;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 */
public class ResponseParser implements ProtocolParser {

    /**
     * 将请求响应对象解析成BaseMode对象
     *
     * @param model 对象模型
     * @return BaseModel
     */
    @Override
    public BaseModel parse(RequestResponseModel model) {
        ResponseModel responseModel = (ResponseModel) model;
        //协议的类型
        int protocolType = responseModel.getProtocolType();//0:connection 1:login 2:add friend 3:send message
        Map<String, String> extras = responseModel.getExtras();
        if (protocolType == 0) {
            ConnectionModel connectionModel = new ConnectionModel();
            connectionModel.setProtocolType(responseModel.getProtocolType());
            connectionModel.setSenderid(responseModel.getSenderid());
            connectionModel.setReceiverid(responseModel.getReceiverid());
            connectionModel.setTimestamp(responseModel.getTimestamp());
            connectionModel.setHost(NetworkUtils.getHost());
            connectionModel.setResponseCode(responseModel.getResponseCode());
            connectionModel.setResponseContent(responseModel.getResponseContent());
            return connectionModel;
        } else if (protocolType == 1) {
            LoginModel loginModel = new LoginModel();
            loginModel.setProtocolType(responseModel.getProtocolType());
            loginModel.setSenderid(responseModel.getSenderid());
            loginModel.setReceiverid(responseModel.getReceiverid());
            loginModel.setTimestamp(responseModel.getTimestamp());
            loginModel.setResponseCode(responseModel.getResponseCode());
            loginModel.setResponseContent(responseModel.getResponseContent());
            return loginModel;
        } else if (protocolType == 2) {
            MessageModel messageModel = new MessageModel();
            return messageModel;
        } else if (protocolType == 3) {
            MessageModel messageModel = new MessageModel();
            messageModel.setProtocolType(responseModel.getProtocolType());
            messageModel.setSenderid(responseModel.getSenderid());
            messageModel.setReceiverid(responseModel.getReceiverid());
            messageModel.setTimestamp(responseModel.getTimestamp());
            messageModel.setResponseCode(responseModel.getResponseCode());
            messageModel.setResponseContent(responseModel.getResponseContent());
            messageModel.setTextMessage(extras.get("textMessage"));
            return messageModel;
        }
        return new BaseModel();
    }
}