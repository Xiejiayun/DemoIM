package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.ConnectionModel;
import com.netease.corp.hzxiejiayun.common.model.LoginModel;
import com.netease.corp.hzxiejiayun.common.model.MessageModel;
import com.netease.corp.hzxiejiayun.common.model.RequestResponseModel;

import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 请求解析器，对于发送过来的请求进行解析
 */
public class RequestParser implements ProtocolParser {

    @Override
    public void parseHeader(RequestResponseModel model) {
        //协议的类型
        int protocolType = model.getProtocolType();//0:connection 1:login 2:add friend 3:send message
        //主机的IP地址
        String host = model.getHost();
        //发送者的用户id
        String senderid = model.getSenderid();
        //接收者的用户id，如果接收者为服务器，则这项为空
        String receiverid = model.getReceiverid();
        //时间戳
        String timestamp = model.getTimestamp();
        //用户的登录状态
        int userStatus = model.getUserStatus();
    }

    @Override
    public void parseContent(RequestResponseModel model) {
        //协议的类型
        int protocolType = model.getProtocolType();//0:connection 1:login 2:add friend 3:send message

        switch (protocolType) {
            case 0:
                parseConnection(model.getExtras());
                break;//处理连接的情况
            case 1:
                parseLogin(model.getExtras());
                break;//处理登录的情况
            case 2:
                parseFriend(model.getExtras());
                break;//处理添加好友的情况
            case 3:
                parseMessage(model.getExtras());
                break;//处理发送消息的情况
            default:
                break;//
        }

    }

    /**
     * 解析连接时候的连接模型
     * @param extras 每次请求中所附带的信息
     * @return ConnectionModel
     */
    private ConnectionModel parseConnection(Map<String, String> extras) {
        ConnectionModel connectionModel = new ConnectionModel();

        return null;
    }

    /**
     * 解析登录时候的登录模型
     * @param extras 每次请求中所附带的信息
     * @return LoginModel
     */
    private LoginModel parseLogin(Map<String, String> extras) {
        return null;
    }

    private void parseFriend(Map<String, String> extras) {
    }

    /**
     * 解析聊天时候的消息模型
     * @param extras 每次请求中所附带的信息
     * @return MessageModel
     */
    private MessageModel parseMessage(Map<String, String> extras) {
        return null;
    }
}