package com.netease.corp.hzxiejiayun.client;


import com.netease.corp.hzxiejiayun.common.io.CommonReader;
import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;
import com.netease.corp.hzxiejiayun.common.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by hzxiejiayun on 2016/6/14.
 * <p/>
 * 默认的IM客户端
 */
public class DefaultIMClient implements IMClient {

    private static List<String> friendList = new ArrayList<>();
    SocketChannel socketChannel = null;
    Selector selector = null;
    String uid = null;
    ByteBuffer send = null;
    ByteBuffer receive = ByteBuffer.allocate(1024);

    public DefaultIMClient() {
        init();
    }

    public static void main(String[] args) {
        DefaultIMClient client = new DefaultIMClient();
        Scanner in = new Scanner(System.in);
        client.basicInstruction(in);
    }

    /**
     * 初始化Selector和SocketChannel
     */
    private void init() {
        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 6666));
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username, String password) {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocolType(1);
        requestModel.setHost(NetworkUtils.getHost());
        requestModel.setTimestamp(DateUtils.format(new Date()));
        //这边以用户的uid作为Senderid
        requestModel.setSenderid(username);
        Map<String, String> extras = new HashMap<>();
        extras.put("username", username);
        extras.put("password", password);
        requestModel.setExtras(extras);

        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.isConnectionPending()) {
                            socketChannel.finishConnect();
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                        }
                    } else if (selectionKey.isReadable()) {
                        try {
                            receive.clear();
                            socketChannel.read(receive);
                            receive.flip();
                            Object obj = CommonReader.getObject(receive);
                            ResponseModel responseModel = (ResponseModel) obj;
                            if (responseModel != null && responseModel.getResponseCode().equals("1")) {
                                uid = username;
                                Map friendMap = responseModel.getExtras();
                                printFriendList(friendMap);
                                return true;
                            }
                            if (responseModel != null && responseModel.getResponseCode().equals("2")) {
                                selectionKey.interestOps(SelectionKey.OP_WRITE);
                                return false;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (selectionKey.isWritable()) {
                        sendRequest(requestModel);
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void friend(String uid, String friendid) {

    }

    @Override
    public boolean message(String senderid, String receiverid) {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocolType(3);
        requestModel.setHost(NetworkUtils.getHost());
        requestModel.setSenderid(senderid);
        requestModel.setReceiverid(receiverid);
        requestModel.setTimestamp(DateUtils.format(new Date()));
        Map<String, String> extras = new HashMap<>();
        requestModel.setExtras(extras);
        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    System.out.println(selectionKey.interestOps());
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.isConnectionPending()) {
                            socketChannel.finishConnect();
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                            new SendThread(socketChannel).start();
                        }
                    } else if (selectionKey.isReadable()) {
                        try {
                            receive.clear();
                            socketChannel.read(receive);
                            receive.flip();
                            Object obj = CommonReader.getObject(receive);
                            ResponseModel responseModel = (ResponseModel) obj;
                            if (responseModel == null)
                                continue;
                            String chattime = responseModel.getTimestamp();
                            String sender = responseModel.getSenderid();
                            String receiver = responseModel.getReceiverid();
                            String msg = responseModel.getExtras().get("textMessage");
                            System.out.println(chattime + " Sender: " + sender + " Receiver: " + receiver);
                            System.out.println(msg);
                        } catch (IOException e) {
                            selectionKey.cancel();
                            System.out.println("客户端读取数据失败，关闭对应通道");
                        }
                    } else if (selectionKey.isWritable()) {
                        sendRequest(requestModel);
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送对应的请求
     *
     * @param requestModel
     * @throws IOException
     */
    private void sendRequest(RequestModel requestModel) throws IOException {
        try {
            send = CommonWriter.setObject(requestModel);
            socketChannel.write(send);
        } catch (IOException e) {
            try {
                socketChannel.isConnectionPending();
                socketChannel.finishConnect();
            } catch (IOException ie) {
                System.out.println("重连失败");
            }
        }
    }

    /**
     * 最基础的信息提示
     *
     * @param in
     */
    public void basicInstruction(Scanner in) {
        System.out.println("||============================||");
        System.out.println("||----------------------------||");
        System.out.println("||-------Operation Menu-------||");
        System.out.println("||----------------------------||");
        System.out.println("||------Choose operations-----||");
        System.out.println("1 ：Login");
        System.out.println("0 ：Exit");
        String command = in.next();
        if (command != null) {
            switch (command) {
                case "1":
                    loginInstruction(in);
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 登录的信息提示
     *
     * @param in 输入
     */
    public void loginInstruction(Scanner in) {
        System.out.println("||============================||");
        System.out.println("||----------------------------||");
        System.out.println("||-------Login Menu-----------||");
        System.out.println("||----------------------------||");
        System.out.println("||---Please input your name---||");
        String username = in.next();
        System.out.println("||-Please input your password-||");
        String password = in.next();
        boolean userLogin = login(username, password);
        if (userLogin) {
            System.out.println("||--------Login succeed-------||");
            mainMenuInstruction(in);
        } else {
            System.out.println("||--------Login failed--------||");
            loginInstruction(in);
        }
    }

    /**
     * 打印出用户登录之后的主界面
     *
     * @param in 输入
     */
    public void mainMenuInstruction(Scanner in) {
        System.out.println("||============================||");
        System.out.println("||----------------------------||");
        System.out.println("||--------Main Menu-----------||");
        System.out.println("||----------------------------||");
        System.out.println("||-Please choose your command-||");
        System.out.println("1 ：Chat");
        System.out.println("2 ：Add Friend");
        System.out.println("3 ：Logout");
        System.out.println("0 ：Exit");
        String command = in.next();
        if (command.equals("1")) {
            //聊天
            chatInstruction(in);
        } else if (command.equals("2")) {
            //添加好友
        } else if (command.equals("3")) {
            //退出登录
        } else if (command.equals("0")) {
            System.exit(0);
        } else {
            System.out.println("输入错误，请重新选择");
            mainMenuInstruction(in);
        }
        loginInstruction(in);
    }

    /**
     * 打印出聊天的提示界面
     *
     * @param in 输入
     */
    public void chatInstruction(Scanner in) {
        System.out.println("||============================||");
        System.out.println("||----------------------------||");
        System.out.println("||--------Chat Menu-----------||");
        System.out.println("||----------------------------||");
        System.out.println("||--Please input the receiver-||");
        String receiver = in.next();
        boolean isFriend = isInFriendList(receiver);
        if (!isFriend) {
            System.out.println("User " + receiver + " is not in your friend list");
            chatInstruction(in);
        }
        message(uid, receiver);
    }


    /**
     * 打印当前用户的好友列表
     *
     * @param friendMap 好友列表映射Map
     */
    public void printFriendList(Map<String, String> friendMap) {
        System.out.println("||============================||");
        System.out.println("||----------------------------||");
        System.out.println("||-------Your FiendList-------||");
        System.out.println("||----------------------------||");
        int index = 1;
        for (String key : friendMap.keySet()) {
            System.out.println("||---" + (index++) + " " + key);
            friendList.add(key);
        }
        System.out.println("||============================||");
    }

    /**
     * 判断用户所输入的接收者是否为用户的好友
     *
     * @param username 接收者姓名
     * @return 是否好友
     */
    public boolean isInFriendList(String username) {
        for (String friendname : friendList) {
            if (StringUtils.equals(username, friendname)) {
                return true;
            }
        }
        return false;
    }
}