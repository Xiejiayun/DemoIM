package com.netease.corp.hzxiejiayun.common.model;

import java.io.Serializable;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 响应模型，包括响应码和响应内容
 */
public class ResponseModel extends RequestResponseModel implements Serializable {

    private String responseCode;

    private String responseContent;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}