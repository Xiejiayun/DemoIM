package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 心跳模型
 */
public class HeartbeatModel extends BaseModel {
    //心跳包的计数，从1开始
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
