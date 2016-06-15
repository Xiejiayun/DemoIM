package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class UserDao{


    boolean add(UserDO userDO) {
        boolean result = true;
        String sql = "insert into table users";
        return result;
    }

    boolean update(UserDO userDO) {
        return false;
    }

    boolean delete(int id) {
        return false;
    }

    BaseDO query(int id) {
        return null;
    }
}
