package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/14.
 * <p/>
 * 主要用来存储用户的模型
 */
public class UserModel {
    //用户id
    private String uid;
    //用户昵称
    private String uname;
    //用户密码
    private String password;
    //用户昵称
    private String nickname;
    //用户生日
    private String birthday;
    //用户头像
    private byte[] avatar;


    public UserModel(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
