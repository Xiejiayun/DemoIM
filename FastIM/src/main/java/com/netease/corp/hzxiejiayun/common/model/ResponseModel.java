package com.netease.corp.hzxiejiayun.common.model;

import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class ResponseModel {
    //协议的类型
    private String protocolType;
    //主机的IP地址
    private String host;
    //用户名
    private String username;
    //时间戳
    private String datetime;
    //用户的登录状态
    private String userStatus;
    //用来存放其他类型数据的Map
    private Map<String, String> extras;

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "protocolType='" + protocolType + '\'' +
                ", host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", datetime='" + datetime + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", extras=" + extras +
                '}';
    }
}
