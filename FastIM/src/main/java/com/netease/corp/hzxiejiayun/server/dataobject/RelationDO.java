package com.netease.corp.hzxiejiayun.server.dataobject;

/**
 * Created by hzxiejiayun on 2016/6/17.
 * <p/>
 * 用来存储用户的好友关系的记录
 */
public class RelationDO {

    private int id;

    private int userid;

    private int frienduid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getFrienduid() {
        return frienduid;
    }

    public void setFrienduid(int frienduid) {
        this.frienduid = frienduid;
    }
}
