package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;
import com.netease.corp.hzxiejiayun.server.dataobject.RelationDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class RelationDao {

    public static void main(String[] args) {
        RelationDao relationDao = new RelationDao();
        List list = relationDao.queryFriend(6);
        System.out.println(list.toString() + list.size());
    }

    boolean add(RelationDO relationDO) {
        boolean result = false;
        int userid = relationDO.getUserid();
        int frienduid = relationDO.getFrienduid();
        String sql = "insert into relation(uid,frienduid) values (" + userid + "," + frienduid + ")";
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    //这边不提供支持这种更新
    boolean update(BaseDO baseDO) {
        return false;
    }

    boolean delete(int id) {
        boolean result = false;
        //这边可能产生SQL注入攻击
        String sql = "delete from relation where id=" + id;
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    RelationDO query(int id) {
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

    List<RelationDO> queryFriend(int uid) {
        List<RelationDO> resultList = new ArrayList<>();
        List<String> result = null;
        String sql = "select relationid from relation where uid=" + uid + " or frienduid=" + uid;
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.get(0) == null)
            return null;
        for (String sid : result) {
            Integer id = Integer.parseInt(sid);
            RelationDO relationDO = query(id);
            resultList.add(relationDO);
        }
        return resultList;
    }


}