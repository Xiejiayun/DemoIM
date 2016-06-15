package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.server.model.UserModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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

    public DefaultIMServer(int port) {
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
        while(true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handleKey(selectionKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            sss = (ServerSocketChannel)selectionKey.channel();
            try {
                client = sss.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Acceptable");
        } else if (selectionKey.isReadable()) {
            client = (SocketChannel)selectionKey.channel();
            receive.clear();
            try {
                client.read(receive);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(receive.array()));
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            System.out.println("Readable");
        } else if (selectionKey.isWritable()) {
            send.flip();
            client = (SocketChannel)selectionKey.channel();
            try {
                client.write(send);
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
            System.out.println("Writable");
        } else if (selectionKey.isConnectable()) {
            System.out.println("Connectable");
        }
    }
}