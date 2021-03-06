package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 通信过程中的消息模型
 */
public class MessageModel extends BaseModel {

    //文本信息
    private String textMessage;
    //图片信息
    private byte[] imageMessage;
    //语音信息
    private byte[] voiceMessage;
    //视频信息
    private byte[] videoMessage;

    public MessageModel(String textMessage) {
        super();
        this.textMessage = textMessage;
    }

    public MessageModel() {
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public byte[] getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(byte[] imageMessage) {
        this.imageMessage = imageMessage;
    }

    public byte[] getVoiceMessage() {
        return voiceMessage;
    }

    public void setVoiceMessage(byte[] voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    public byte[] getVideoMessage() {
        return videoMessage;
    }

    public void setVideoMessage(byte[] videoMessage) {
        this.videoMessage = videoMessage;
    }
}
