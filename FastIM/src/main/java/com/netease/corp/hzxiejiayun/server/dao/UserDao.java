package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class UserDao{

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        UserDO userDO = new UserDO();
        userDO.setUid("jack");
        userDO.setUname("tom");
        userDO.setPasswd("12345678");
        userDao.add(userDO);
        userDao.update(userDO);
        String uid = "jack";
        userDao.delete(uid);
    }

    boolean add(UserDO userDO) {
        boolean result = false;
        String uid = userDO.getUid();
        String uname = userDO.getUname();
        String passwd = userDO.getPasswd();
        String sql = "insert into users(uid, uname, passwd) values('"+uid+"','"+uname+"','"+passwd+"')";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean update(UserDO userDO) {
        boolean result;
        String uid = userDO.getUid();
        String uname = userDO.getUname();
        String passwd = userDO.getPasswd();
        String sql = "update users set uname = '"+uname+"', passwd = '"+passwd+"'\n" +
                "where uid = '"+uid+"'";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    boolean delete(String uid) {
        boolean result = false;
        String sql = "delete from users where uid='"+uid+"'";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    BaseDO query(String uid) {
        String sql = "select * from users where uid='"+uid+"'";
        return null;
    }
}
