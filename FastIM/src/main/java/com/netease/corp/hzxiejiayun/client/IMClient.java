package com.netease.corp.hzxiejiayun.client;

/**
 * Created by hzxiejiayun on 2016/6/14.
 * <p/>
 * 客户端接口
 */
public interface IMClient {

    /**
     * 根据用户名和密码连接服务器
     *
     * @param uid 用户id
     * @param pwd 用户密码
     */
    boolean login(String uid, String pwd);

    /**
     * 添加好友的操作
     *
     * @param uid      用户id
     * @param friendid 好友id
     */
    void friend(String uid, String friendid);

    /**
     * @param senerid    发送者id
     * @param receiverid 接收者id
     * @param message    发送消息
     */
    void message(String senerid, String receiverid, String message);


}
