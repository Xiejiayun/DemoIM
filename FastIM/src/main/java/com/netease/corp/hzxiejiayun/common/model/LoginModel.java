package com.netease.corp.hzxiejiayun.common.model;

/**
 * Created by hzxiejiayun on 2016/6/15.
 * <p/>
 * 登录模型
 */
public class LoginModel extends BaseModel {
    //用户名
    private String username;
    //密码
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
