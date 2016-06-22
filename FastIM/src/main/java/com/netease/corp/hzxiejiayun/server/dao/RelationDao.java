package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.RelationDO;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class RelationDao {

    UserDao userDao = UserDao.UserDaoHandler.getUserDao();

    public static void main(String[] args) {
        RelationDao relationDao = new RelationDao();
        List list = relationDao.queryFriend(6);
        System.out.println(list.toString() + list.size());
    }

    public boolean add(RelationDO relationDO) {
        boolean result = false;
        int userid = relationDO.getUserid();
        int frienduid = relationDO.getFrienduid();
        String sql = "insert into relation(uid,frienduid) values (" + userid + "," + frienduid + ")";
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    //这边不提供支持这种更新
    public boolean update(RelationDO baseDO) {
        return false;
    }

    public boolean delete(int id) {
        boolean result = false;
        //这边可能产生SQL注入攻击
        String sql = "delete from relation where id=" + id;
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    public RelationDO query(int id) {
        RelationDO relationDO = new RelationDO();
        List<String> result = null;
        String sql = "select * from relation where relationid=" + id;
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.get(0) == null)
            return null;
        int uid = Integer.parseInt(result.get(1));
        int frienduid = Integer.parseInt(result.get(2));
        relationDO.setId(id);
        relationDO.setUserid(uid);
        relationDO.setFrienduid(frienduid);
        return relationDO;
    }

    public List<UserDO> queryFriend(int uid) {
        List<UserDO> resultList = new ArrayList<>();
        Set<Integer> friendIds = new HashSet<>();
        List<String> result = null;
        String sql = "select relationid from relation where uid=" + uid + " or frienduid=" + uid;
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.get(0) == null)
            return null;
        for (String sid : result) {
            Integer id = Integer.parseInt(sid);
            RelationDO relationDO = query(id);
            friendIds.add(relationDO.getUserid());
            friendIds.add(relationDO.getFrienduid());
        }
        friendIds.remove(uid);
        for (int friendid : friendIds) {
            UserDO userDO = userDao.query(friendid);
            resultList.add(userDO);
        }
        return resultList;
    }

    public static class RelationDaoHandler {

        private static RelationDao relationDao = new RelationDao();

        public static RelationDao getRelationDao() {
            return relationDao;
        }
    }

}