package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.common.io.CommonReader;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.model.UserModel;
import com.netease.corp.hzxiejiayun.server.processor.DefaultProcessor;
import com.netease.corp.hzxiejiayun.server.processor.Processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMServer implements IMServer {

    private static int port = 6666;
    Selector selector;
    private ByteBuffer send = ByteBuffer.allocate(1024);
    private ByteBuffer receive = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        DefaultIMServer defaultIMServer = new DefaultIMServer(port);
        defaultIMServer.listen();
    }

    public DefaultIMServer() {
        init(port);
    }

    public DefaultIMServer(int port) {
        init(port);
    }

    private void init(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserModel> getAllFriendsList(String uid) {
        return null;
    }

    @Override
    public void forwardMessage() {

    }

    @Override
    public void listen() {
        while (true) {
            try {
                int n = selector.select();
                if (n == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (selectionKeys.size() > 0) {
//                    System.out.println("Incoming events " + selectionKeys.size());
                }
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    handleKey(selectionKey);
                    it.remove();
                }
                selectionKeys.clear();
            } catch (IOException e) {
                System.out.println("发生了IO异常");
            }

        }
    }

    @Override
    public void userLoginAuthorize() {

    }

    @Override
    public void friendRequest() {

    }

    private void handleKey(SelectionKey selectionKey) {
        ServerSocketChannel sss = null;
        SocketChannel client = null;
        if (selectionKey.isAcceptable()) {
            sss = (ServerSocketChannel) selectionKey.channel();
            try {
                client = sss.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Acceptable");
        } else if (selectionKey.isReadable()) {
            try {
                client = (SocketChannel) selectionKey.channel();
                client.configureBlocking(false);
                receive.clear();
                client.read(receive);
                receive.flip();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Object obj = CommonReader.getObject(receive);
            RequestModel request = (RequestModel) obj;
            System.out.println(request);
            try {
                client.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
//            handleRequest(request);
        }
    }

    private void handleRequest(RequestModel request) {
        Processor processor = new DefaultProcessor();
        ResponseModel response = processor.createResponse();
        processor.service(request, response);
    }
}