package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.server.model.UserModel;

import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public interface IMServer {

    /**
     * 根据用户的id获取其所有的好友列表
     * @param uid
     * @return
     */
    List<UserModel> getAllFriendsList(String uid);

    /**
     * 转发各类请求，通过分析不同的Socket的类型和用途来进行区别对待
     */
    void forwardMessage();

    void listen();

    void userLoginAuthorize();

    void friendRequest();




}
