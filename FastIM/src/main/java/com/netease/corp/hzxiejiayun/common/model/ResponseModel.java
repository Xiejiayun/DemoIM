package com.netease.corp.hzxiejiayun.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 响应模型，包括响应码和响应内容
 */
public class ResponseModel extends RequestResponseModel implements Serializable {

    private String responseCode;
    private String responseContent;

    public ResponseModel(String responseCode, String responseContent) {
        super();
        this.responseCode = responseCode;
        this.responseContent = responseContent;
        this.setExtras(new HashMap<String, Object>());
    }

    public ResponseModel() {
        super();
    }

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

    @Override
    public String toString() {
        return "ResponseModel{" +
                "responseCode='" + responseCode + '\'' +
                ",  ='" + responseContent + '\'' +
                "} " + super.toString();
    }
}