package com.netease.corp.hzxiejiayun.server.model;

/**
 * Created by hzxiejiayun on 2016/6/14.
 *
 * 主要用来存储用户的模型
 */

public class UserModel {
    //用户id
    private String uid;
    //用户昵称
    private String uname;

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
}
