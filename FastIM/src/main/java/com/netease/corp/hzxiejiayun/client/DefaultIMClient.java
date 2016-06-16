package com.netease.corp.hzxiejiayun.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMClient implements IMClient {

    SocketChannel socketChannel = null;
    Selector selector = null;
    ByteBuffer send = ByteBuffer.wrap("data come frpom client".getBytes());
    ByteBuffer receive = ByteBuffer.allocate(1024);

    public DefaultIMClient() {
    }



    public static void main(String[] args) {
        DefaultIMClient client = new DefaultIMClient();
        Scanner sin = new Scanner(System.in);
        System.out.println("Please input the user id");
        String uid = sin.next();
        System.out.println("Please input the password");
        String pwd = sin.next();
        client.connect(uid, pwd);
    }

    @Override
    public void connect(String uid, String pwd) {

        try {
            socketChannel = SocketChannel.open();
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 6666));
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    socketChannel = (SocketChannel) selectionKey.channel();
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.isConnectionPending()) {
                            socketChannel.finishConnect();
                            System.out.println("Connect completely");
                            try {
                                socketChannel.write(send);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (selectionKey.isReadable()) {
                        try {
                            receive.clear();
                            socketChannel.read(receive);
                            System.out.println(new String(receive.array()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (selectionKey.isWritable()) {
                        receive.flip();
                        try {
                            send.flip();
                            socketChannel.write(send);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addFriend(String uid, String friendid) {

    }

    @Override
    public void message(String receiverid, String message) {

    }
}