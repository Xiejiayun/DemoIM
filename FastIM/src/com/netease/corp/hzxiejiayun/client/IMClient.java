package com.netease.corp.hzxiejiayun.client;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public interface IMClient {

    /**
     * 根据用户名和密码连接服务器
     * @param uid 用户id
     * @param pwd 用户密码
     */
    void connect(String uid, String pwd);

    void addFriend(String uid, String friendid);

    void message(String receiverid, String message);


}
