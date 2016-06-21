package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.OperationDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class OperationDao {
    boolean add(OperationDO baseDO) {
        boolean result = false;
        String sql = "add ";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean update(OperationDO baseDO) {
        boolean result = false;
        String sql = "update ";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean delete(int id) {
        boolean result = false;
        String sql = "delete ";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    OperationDO query(int id) {
        boolean result = false;
        String sql = "query ";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return null;
    }
}