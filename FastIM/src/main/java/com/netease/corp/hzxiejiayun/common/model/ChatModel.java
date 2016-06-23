package com.netease.corp.hzxiejiayun.common.model;

import java.io.Serializable;

/**
 * Created by hzxiejiayun on 2016/6/23.
 */
public class ChatModel implements Serializable{

    //聊天ID
    private int chatid;
    //聊天时间
    private String chattime;
    //消息发送者
    private String sender;
    //消息接收者
    private String receiver;
    //发送消息内容
    private String message;
    //发送消息的状态
    private String status;

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getChattime() {
        return chattime;
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
}