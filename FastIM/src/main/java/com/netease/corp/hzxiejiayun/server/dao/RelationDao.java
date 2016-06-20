package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class RelationDao extends DaoSupport {

    @Override
    boolean add(BaseDO baseDO) {
        boolean result = false;
        String sql = "insert into relation()";

        return false;
    }

    @Override
    boolean update(BaseDO baseDO) {
        return false;
    }

    @Override
    boolean delete(int id) {
        return false;
    }

    @Override
    BaseDO query(int id) {
        return null;
    }
}
