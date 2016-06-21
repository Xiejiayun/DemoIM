package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class UserDao {

    public static class UserDaoHandler {

        private static UserDao userDao = new UserDao();

        public static UserDao getUserDao() {
            return userDao;
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        userDao.query("jack", "12345678");
    }

    public boolean add(UserDO userDO) {
        boolean result = false;
        String uid = userDO.getUid();
        String uname = userDO.getUname();
        String passwd = userDO.getPasswd();
        String sql = "insert into users(uid, uname, passwd) values('" + uid + "','" + uname + "','" + passwd + "')";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    public boolean update(UserDO userDO) {
        boolean result;
        String uid = userDO.getUid();
        String uname = userDO.getUname();
        String passwd = userDO.getPasswd();
        String sql = "update users set uname = '" + uname + "', passwd = '" + passwd + "'\n" +
                "where uid = '" + uid + "'";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    public boolean delete(String uid) {
        boolean result = false;
        String sql = "delete from users where uid='" + uid + "'";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    public UserDO query(String uid) {
        UserDO user = new UserDO();
        List<String> result = null;
        String sql = "select * from users where uid='" + uid + "'";
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null)
            return null;
        int id = Integer.parseInt(result.get(0));
        String uname = result.get(2);
        String password = result.get(3);
        user.setId(id);
        user.setUid(uid);
        user.setUname(uname);
        user.setPasswd(password);
        return user;
    }

    public UserDO query(String uid, String password) {
        UserDO user = new UserDO();
        List<String> result = null;
        String sql = "select * from users where uid='" + uid + "' and passwd='" + password + "'";
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.get(0) == null)
            return null;
        Integer id = Integer.parseInt(result.get(0));
        String uname = result.get(2);
        user.setId(id);
        user.setUid(uid);
        user.setUname(uname);
        user.setPasswd(password);
        return user;
    }
}