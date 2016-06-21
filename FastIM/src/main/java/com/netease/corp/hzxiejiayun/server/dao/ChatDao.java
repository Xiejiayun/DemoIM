package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.common.util.DateUtils;
import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class ChatDao {

    //增加聊天记录的操作
    boolean add(ChatDO chatDO) {
        boolean result = false;
        String chattime = DateUtils.format(chatDO.getChattime());
        String sender = chatDO.getSender();
        String receiver = chatDO.getReceiver();
        String message = chatDO.getMessage();
        String status = chatDO.getStatus();
        String sql = "insert into chat(chattime, sender, receiver, message, status) values ('"+chattime+"','"+sender+"','"+receiver+"','"+message+"', '"+status+"')";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    //在这边应当是没办法更新聊天的记录的
    boolean update(ChatDO chatDO) {
        boolean result = false;
        String sql = "update";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    boolean delete(int id) {
        boolean result = false;
        String sql = "delete from chat where ";
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    ChatDO query(int id) {
        return null;
    }
}
