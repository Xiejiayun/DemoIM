package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/14.
 * <p/>
 * 基础对象模型，主要存储的内容就是传输的元信息
 */
public class BaseModel {
    //协议的类型
    private int protocolType;//0:connection 1:login 2:add friend 3:send message 4:login response
    //消息id,每个客户端从1开始递增
    private int messageid;
    //发送者的用户id
    private String senderid;
    //接收者的用户id，如果接收者为服务器，则这项为空
    private String receiverid;
    //发送消息的具体内容
    private String message;
    //用户登录的状态
    private String status;
    //发送消息的时间戳
    private String timestamp;
    //响应码
    private String responseCode;
    //响应内容
    private String responseContent;

    public BaseModel() {
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}