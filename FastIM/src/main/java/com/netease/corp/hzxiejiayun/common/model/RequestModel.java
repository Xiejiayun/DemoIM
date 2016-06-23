package com.netease.corp.hzxiejiayun.common.model;


import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 请求模型
 */
public class RequestModel extends RequestResponseModel implements Serializable {

    public RequestModel() {
        super();
        this.setExtras(new HashMap<String, Object>());
    }
}