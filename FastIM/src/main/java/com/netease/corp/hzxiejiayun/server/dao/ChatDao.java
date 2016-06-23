package com.netease.corp.hzxiejiayun.server.dao;

import com.netease.corp.hzxiejiayun.server.dataobject.ChatDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxiejiayun on 2016/6/15.
 */
public class ChatDao {

    public static void main(String[] args) {
//        ChatDO chatDO = new ChatDO();
//        chatDO.setSender("jeremy");
//        chatDO.setReceiver("tom");
//        chatDO.setChattime(DateUtils.format(new Date()));
//        chatDO.setMessage("hi");
//        chatDO.setStatus("");
        ChatDao chatDao = ChatDao.ChatDaoHandler.getChatDao();
        ChatDO chatDO = chatDao.query(1);
        List<ChatDO> chatDOs = chatDao.queryUnreadMessage("tom");
        for (ChatDO chat : chatDOs) {
            System.out.println(chat);
        }
    }

    //增加聊天记录的操作
    public boolean add(ChatDO chatDO) {
        boolean result = false;
        String chattime = chatDO.getChattime();
        String sender = chatDO.getSender();
        String receiver = chatDO.getReceiver();
        String message = chatDO.getMessage();
        String status = chatDO.getStatus();
        String sql = "insert into chat(chattime, sender, receiver, message, status) values ('" + chattime + "','" + sender + "','" + receiver + "','" + message + "', '" + status + "')";
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    //在这边更新的话最主要还是需要更新消息的状态，消息的状态
    public boolean update(ChatDO chatDO) {
        boolean result = false;
        String sql = "update chat set status='" + chatDO.getStatus() + "' where chatid=" + chatDO.getChatid();
        DaoUtil.getConnection();
        DaoUtil.executeSQL(sql);
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        String sql = "delete from chat where chatid=" + id;
        DaoUtil.getConnection();
        result = DaoUtil.executeSQL(sql);
        return result;
    }

    public ChatDO query(int id) {
        ChatDO chat = new ChatDO();
        List<String> result = null;
        String sql = "select * from chat where chatid=" + id;
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.size() == 0)
            return null;
        String chattime = result.get(1);
        String sender = result.get(2);
        String receiver = result.get(3);
        String message = result.get(4);
        String status = result.get(5);
        chat.setChatid(id);
        chat.setChattime(chattime);
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage(message);
        chat.setStatus(status);
        return chat;
    }

    /**
     * 根据一个接收用户的id获取所有未读的消息
     *
     * @param receiver 接收者
     * @return
     */
    public List<ChatDO> queryUnreadMessage(String receiver) {
        List<ChatDO> unreadList = new ArrayList<>();
        List<String> result = null;
        String sql = "select chatid from chat where receiver='" + receiver + "' and status='n'";
        DaoUtil.getConnection();
        result = DaoUtil.doSql(sql);
        if (result == null || result.size() == 0 || result.get(0) == null)
            return new ArrayList<>();
        for (String chatid : result) {
            Integer id = Integer.parseInt(chatid);
            ChatDO chatDO = query(id);
            unreadList.add(chatDO);
        }
        return unreadList;
    }

    public static class ChatDaoHandler {

        private static ChatDao chatDao = new ChatDao();

        public static ChatDao getChatDao() {
            return chatDao;
        }
    }

}