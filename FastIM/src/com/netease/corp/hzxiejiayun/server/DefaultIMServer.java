package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.model.UserModel;

import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMServer implements IMServer {
    @Override
    public List<UserModel> getAllFriendsList(String uid) {
        return null;
    }

    @Override
    public void postMessage() {

    }
}
