package com.netease.corp.hzxiejiayun.client;


import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.util.NetworkUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMClient implements IMClient {

    SocketChannel socketChannel = null;
    Selector selector = null;
    ByteBuffer send = null;
    ByteBuffer receive = ByteBuffer.allocate(1024);

    public DefaultIMClient() {
    }


    public static void main(String[] args) {
        DefaultIMClient client = new DefaultIMClient();
        Scanner in = new Scanner(System.in);
        client.basicInstruction(in);
    }

    @Override
    public void login(String username, String password) {
        RequestModel requestModel = new RequestModel();
        requestModel.setProtocolType(1);
        requestModel.setHost(NetworkUtils.getHost());
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("username", username);
        extras.put("password", password);
        requestModel.setExtras(extras);

        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 6666));
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    socketChannel = (SocketChannel) selectionKey.channel();
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.isConnectionPending()) {
                            socketChannel.finishConnect();
                            System.out.println("Connect completely");
                            try {
                                send = CommonWriter.setObject(requestModel);
                                System.out.println(send.toString());
                                socketChannel.write(send);
                                new SendThread(socketChannel).start();
                            } catch (IOException e) {
                                try {
                                    socketChannel.isConnectionPending();
                                    socketChannel.finishConnect();
                                } catch (IOException ie) {
                                    System.out.println("重连失败");
                                }
                            }
                        }
                    } else if (selectionKey.isReadable()) {
                        try {
                            receive.clear();
                            socketChannel.read(receive);
                            System.out.println(new String(receive.array()));
                        } catch (IOException e) {
                        }
                    } else if (selectionKey.isWritable()) {
                        receive.flip();
                        try {
                            send.flip();
                            send = CommonWriter.setObject(requestModel);
                            socketChannel.write(send);
                        } catch (IOException e) {
                        }
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
    public void message(String senderid, String receiverid, String message) {

    }

    public void basicInstruction(Scanner in) {
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||-------Operation Menu-------||");
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||------Choose operations-----||");
        System.out.println("1 ：Login");
        System.out.println("2 ：Friend List");
        System.out.println("3 ：Message Service");
        System.out.println("0 ：Exit");
        String command = in.next();
        if (command != null) {
            switch (command) {
                case "1":
                    loginInstruction(in);
                    break;
                case "2":
                    friendListInstruction(in);
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    break;
            }

        }
    }

    public void loginInstruction(Scanner in) {
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||-------Login Menu-----------||");
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||---Please input your name---||");
        String username = in.next();
        System.out.println("||-Please input your password-||");
        String password = in.next();
        login(username, password);
    }

    public void friendListInstruction(Scanner in) {
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||-------FiendList Menu-------||");
        System.out.println("||----------------------------||");
        System.out.println("||----------------------------||");
        System.out.println("||-Please input your password-||");
        String password = in.next();
        friend("", password);
    }

}