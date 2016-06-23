package com.netease.corp.hzxiejiayun.server.service;

import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.ChatModel;
import com.netease.corp.hzxiejiayun.common.model.LoginModel;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.protocol.ProtocolParser;
import com.netease.corp.hzxiejiayun.common.protocol.RequestParser;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.server.CachedHeartBeat;
import com.netease.corp.hzxiejiayun.server.convertor.ChatConvertor;
import com.netease.corp.hzxiejiayun.server.dao.ChatDao;
import com.netease.corp.hzxiejiayun.server.dao.RelationDao;
import com.netease.corp.hzxiejiayun.server.dao.UserDao;
import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;
import com.netease.corp.hzxiejiayun.server.dataobject.UserDO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class LoginService implements Service {
    private ProtocolParser protocolParser = new RequestParser();
    private UserDao userDao = UserDao.UserDaoHandler.getUserDao();
    private RelationDao relationDao = RelationDao.RelationDaoHandler.getRelationDao();
    private ChatDao chatDao = ChatDao.ChatDaoHandler.getChatDao();

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
            responseModel.setExtras(new HashMap<String, Object>());
            System.out.println("Response is " + responseModel);
            sendBuff = CommonWriter.setObject(responseModel);
            try {
                socketChannel.write(sendBuff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            CachedHeartBeat.put(request.getSenderid(), request.getTimestamp());
            System.out.println("登录成功！");
            response.setHost(NetworkUtils.getHost());
            response.setProtocolType(4);
            response.setResponseCode("1");
            response.setResponseContent("success login");
            //处理获取所有好友列表
            List<UserDO> friends = relationDao.queryFriend(userDO.getId());
            Map<String, Object> extras = new HashMap<>();
            List<String> friendList = new ArrayList<>();
            for (UserDO user : friends) {
                if (user == null)
                    continue;
                friendList.add(user.getUid());
            }
            extras.put("friendList", friendList);
            //处理未读消息
            List<ChatDO> chatDOs = chatDao.queryUnreadMessage(userDO.getUid());
            List<ChatModel> chatModels = new ArrayList<>();
            for (ChatDO chatDO : chatDOs) {
                ChatModel chatModel = ChatConvertor.doTOModel(chatDO);
                chatModels.add(chatModel);
            }
            extras.put("unreadMessages", chatModels);
            response.setExtras(extras);
            System.out.println("Response is " + response);
            sendBuff = CommonWriter.setObject(response);
            System.out.println(sendBuff);
            try {
                socketChannel.write(sendBuff);
                //更新消息的状态至已读
                for (ChatDO chatDO : chatDOs) {
                    chatDO.setStatus("y");
                    chatDao.update(chatDO);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}