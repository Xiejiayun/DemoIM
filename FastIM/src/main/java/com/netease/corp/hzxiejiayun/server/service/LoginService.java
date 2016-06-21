package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.LoginModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.server.dao.UserDao;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class LoginService implements Service {
    private ProtocolParser protocolParser = new RequestParser();
    private UserDao userDao = UserDao.UserDaoHandler.getUserDao();

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {
        LoginModel loginModel = (LoginModel) protocolParser.parse(request);
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        UserDO userDO = userDao.query(username, password);
        ByteBuffer sendBuff = null;
        if (userDO == null) {
            System.out.println("用户名和密码错误！");
        } else {
            System.out.println("登录成功！");
            response.setHost(NetworkUtils.getHost());
            response.setResponseCode("1");
            response.setResponseContent("success login");
            response.setExtras(new HashMap<String, String>());
            System.out.println("Response is "+response);
            sendBuff = CommonWriter.setObject(response);
            System.out.println("ByteBuffer is " + sendBuff);
            try {
                socketChannel.write(sendBuff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
