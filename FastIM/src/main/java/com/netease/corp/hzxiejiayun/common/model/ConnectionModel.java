package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/20.
 *
 * 第一次和Socket建立连接时候传
 */
public class ConnectionModel {
    //协议的类型
    private int protocolType;//0:connection 1:login 2:add friend 3:send message
    //主机的IP地址
    private String host;
    //发送者的用户id
    private String senderid;
    //时间戳
    private String timestamp;
    //用户的登录状态
    private int userStatus;

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

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
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
}
