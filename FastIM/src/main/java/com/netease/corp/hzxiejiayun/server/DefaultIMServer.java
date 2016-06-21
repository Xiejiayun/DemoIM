package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.common.io.CommonReader;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.server.processor.DefaultProcessor;
import com.netease.corp.hzxiejiayun.server.processor.Processor;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMServer implements IMServer {

    private Map<String, SocketChannel> cachedSockets = new HashMap<>();
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
            System.out.println("服务端启动，开始监听"+port+"端口……");
        } catch (BindException bindException) {
            System.out.println("地址已经在使用……");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    handleKey(selectionKey);
                }
            } catch (IOException e) {
                System.out.println("发生了IO异常");
            }
        }
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
                System.out.println("接收Socket、设置非阻塞或注册事件失败");
            }
            System.out.println("Acceptable");
        } else if (selectionKey.isReadable()) {
            System.out.println("服务端读取数据……");
            client = (SocketChannel) selectionKey.channel();
            receive.clear();
            try {
                while (client.read(receive) > 0) {
                    System.out.println(receive);
                    receive.flip();
                    System.out.println(receive.asCharBuffer().toString());
                }
                Object obj = CommonReader.getObject(receive);
                RequestModel request = (RequestModel) obj;
                System.out.println(request);
                handleRequest(request, client);
                //在这边在缓存的Sockets里面添加用户和对应Socket的映射关系
                cachedSockets.put(request.getSenderid(), client);
                selectionKey.interestOps(SelectionKey.OP_READ);
                client.register(selector, SelectionKey.OP_WRITE);
            } catch (IOException e) {
                System.out.println("读取客户端发送数据失败，终止失败操作");
                return;
            }

        } else if (selectionKey.isWritable()) {
            client = (SocketChannel) selectionKey.channel();
            send.clear();
            try {
                client.write(send);
                selectionKey.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param request
     */
    private void handleRequest(RequestModel request, SocketChannel socket) {
        Processor processor = new DefaultProcessor();
        ResponseModel response = processor.createResponse();
        processor.service(request, response,socket);
    }
}