package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.LoginModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.server.dao.RelationDao;
import com.netease.corp.hzxiejiayun.server.dao.UserDao;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class LoginService implements Service {
    private ProtocolParser protocolParser = new RequestParser();
    private UserDao userDao = UserDao.UserDaoHandler.getUserDao();
    private RelationDao relationDao = RelationDao.RelationDaoHandler.getRelationDao();

    @Override
    public void service(RequestModel request, ResponseModel response, SocketChannel socketChannel) {
        LoginModel loginModel = (LoginModel) protocolParser.parse(request);
        String username = loginModel.getUsername();
        String password = loginModel.getPassword();
        UserDO userDO = userDao.query(username, password);
        ByteBuffer sendBuff = null;
        if (userDO == null) {
            System.out.println("用户名和密码错误！");
            ResponseModel responseModel = new ResponseModel();
            responseModel.setHost(NetworkUtils.getHost());
            responseModel.setProtocolType(4);//响应登录
            responseModel.setResponseCode("2");
            responseModel.setResponseContent("failed login");
            responseModel.setExtras(new HashMap<String, String>());
            System.out.println("Response is " + responseModel);
            sendBuff = CommonWriter.setObject(responseModel);
            try {
                socketChannel.write(sendBuff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("登录成功！");
            response.setHost(NetworkUtils.getHost());
            response.setProtocolType(4);
            response.setResponseCode("1");
            response.setResponseContent("success login");
            List<UserDO> friends = relationDao.queryFriend(userDO.getId());
            Map<String, String> extras = new HashMap<>();
            for (UserDO relationDO : friends) {
                if (relationDO == null)
                    continue;
                extras.put(relationDO.getUid(), relationDO.getUname());
            }
            response.setExtras(extras);
            System.out.println("Response is " + response);
            sendBuff = CommonWriter.setObject(response);
            try {
                socketChannel.write(sendBuff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}