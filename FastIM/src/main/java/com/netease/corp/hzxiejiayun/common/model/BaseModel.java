package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class BaseModel {
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

    public BaseModel(int messageid, String senderid, String receiverid, String message, String status) {
        this.messageid = messageid;
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.message = message;
        this.status = status;
    }

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
}
