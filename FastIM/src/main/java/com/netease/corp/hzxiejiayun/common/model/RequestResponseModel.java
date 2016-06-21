package com.netease.corp.hzxiejiayun.common.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 最基础的通信对象，其中包含一系列通信的参数
 */
public class RequestResponseModel implements Serializable {
    //协议的类型
    private int protocolType;//0:connection 1:login 2:add friend 3:send message
    //主机的IP地址
    private String host;
    //发送者的用户id
    private String senderid;
    //接收者的用户id，如果接收者为服务器，则这项为空
    private String receiverid;
    //时间戳
    private String timestamp;
    //用户的登录状态
    private int userStatus;
    //用来存放其他类型数据的Map
    private Map<String, String> extras;

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "RequestResponseModel{" +
                "protocolType=" + protocolType +
                ", host='" + host + '\'' +
                ", senderid='" + senderid + '\'' +
                ", receiverid='" + receiverid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", userStatus=" + userStatus +
                ", extras=" + extras.toString() +
                '}';
    }
}