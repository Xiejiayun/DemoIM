package com.netease.corp.hzxiejiayun.server.dataobject;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class UserDO extends BaseDO {
    private int id;
    private String uid;
    private String uname;
    private String passwd;

    public UserDO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}