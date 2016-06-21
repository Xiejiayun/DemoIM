package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.UserStatusDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class UserStatusDao {

    boolean add(UserStatusDO baseDO) {
        boolean result = false;
        String sql = "add ";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    boolean update(UserStatusDO baseDO) {
        boolean result = false;
        String sql = "update ";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    boolean delete(int id) {
        boolean result = false;
        String sql = "delete ";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    UserStatusDO query(int id) {
        return null;
    }
}
