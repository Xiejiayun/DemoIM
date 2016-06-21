package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 第一次和Socket建立连接时候传
 */
public class ConnectionModel extends BaseModel{

    //主机的IP地址
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
