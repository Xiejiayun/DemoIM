package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;
import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class ChatDao {
    boolean add(ChatDO chatDO) {
        boolean result = false;
//        String sql = "insert into users(uid, uname, passwd) values('"+uid+"','"+uname+"','"+passwd+"')";
        DaoUtil.getConnection();
//        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean update(ChatDO chatDO) {
        boolean result = false;
        String sql = "update";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean delete(int id) {
        boolean result = false;
        String sql = "delete";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    BaseDO query(int id) {
        return null;
    }
}
