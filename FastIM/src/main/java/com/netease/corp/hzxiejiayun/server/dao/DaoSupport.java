package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.BaseDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public abstract class DaoSupport {

    abstract boolean add(BaseDO baseDO);

    abstract boolean update(BaseDO baseDO);

    abstract boolean delete(int id);

    abstract BaseDO query(int id);

}
