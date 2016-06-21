package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.*;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 请求解析器，对于发送过来的请求进行解析
 */
public class RequestParser implements ProtocolParser {

    /**
     * 将请求响应对象解析成BaseMode对象
     *
     * @param model 对象模型
     * @return BaseModel
     */
    @Override
    public BaseModel parse(RequestResponseModel model) {
        //协议的类型
        int protocolType = model.getProtocolType();//0:connection 1:login 2:add friend 3:send message
        Map<String, String> extras = model.getExtras();
        if (protocolType == 0) {
            ConnectionModel connectionModel = new ConnectionModel();
            connectionModel.setProtocolType(model.getProtocolType());
            connectionModel.setSenderid(model.getSenderid());
            connectionModel.setReceiverid(model.getReceiverid());
            connectionModel.setTimestamp(model.getTimestamp());
            connectionModel.setHost(NetworkUtils.getHost());
            return connectionModel;
        } else if (protocolType == 1) {
            LoginModel loginModel = new LoginModel();
            loginModel.setProtocolType(model.getProtocolType());
            loginModel.setSenderid(model.getSenderid());
            loginModel.setReceiverid(model.getReceiverid());
            loginModel.setTimestamp(model.getTimestamp());
            loginModel.setUsername(extras.get("username"));
            loginModel.setPassword(extras.get("password"));
            return loginModel;
        } else if (protocolType == 2) {
            MessageModel messageModel = new MessageModel();
            return messageModel;
        } else if (protocolType == 3) {
            MessageModel messageModel = new MessageModel();
            messageModel.setProtocolType(model.getProtocolType());
            messageModel.setSenderid(model.getSenderid());
            messageModel.setReceiverid(model.getReceiverid());
            messageModel.setTimestamp(model.getTimestamp());
            messageModel.setTextMessage(extras.get("textMessage"));
            return messageModel;
        }
        return new BaseModel();
    }

}