package com.netease.corp.hzxiejiayun.server;

import com.netease.corp.hzxiejiayun.common.io.CommonReader;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;
import com.netease.corp.hzxiejiayun.common.model.ResponseModel;
import com.netease.corp.hzxiejiayun.common.util.DateUtils;
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
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hzxiejiayun on 2016/6/14.
 */
public class DefaultIMServer implements IMServer {

    private static int port = 6666;
    Selector selector;
    private ByteBuffer send = ByteBuffer.allocate(20480);
    private ByteBuffer receive = ByteBuffer.allocate(20480);

    public DefaultIMServer() {
        init(port);
    }

    public DefaultIMServer(int port) {
        init(port);
    }

    public static void main(String[] args) {
        DefaultIMServer defaultIMServer = new DefaultIMServer(port);
        defaultIMServer.listen();
    }

    private void init(int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端启动，开始监听" + port + "端口……");
            new Thread(new CachedSocket()).start();
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
            System.out.println("接收来自客户端的数据……");
            client = (SocketChannel) selectionKey.channel();
            receive.clear();
            RequestModel request = null;
            try {
                while (client.read(receive) > 0) {
                    receive.flip();
                }
                Object obj = CommonReader.getObject(receive);
                request = (RequestModel) obj;
                if (request == null)
                    return;
                System.out.println("接收到从客户端" + request.getSenderid() + "传输过来的消息" + request.toString());
                //在这边在缓存的Sockets里面添加用户和对应Socket的映射关系
                if (request.getProtocolType() == 3) {
                    CachedSocket.cachedSockets.put(request.getSenderid(), client);
                    CachedSocket.cachedKeys.put(request.getSenderid(), selectionKey);
                }
                handleRequest(request, client);
                selectionKey.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                //这个步骤需要将对应的selectionKey移除
                String user = CachedSocket.getUserFromSelectionKey(selectionKey);
                boolean isUserOnline = isUserOnline(user);
                if (!isUserOnline) {
                    selectionKey.cancel();
                    System.out.println("关闭对应的通道");
                }
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

    private boolean isUserOnline(String senderid) {
        boolean isOnline = true;
        while (true) {
            Date current = new Date();
            String heartbeat = CachedHeartBeat.heartBeatData.get(senderid);
            if (heartbeat == null)
                return false;
            Date heartBeatDate = DateUtils.parse(heartbeat);
            long gap = DateUtils.gap(current, heartBeatDate);
            double seconds = (double) gap / 1000;
            if (seconds > 15) {
                //如果用户超时三分钟，则删除指定用户
                isOnline = false;
                CachedSocket.cachedSockets.remove(senderid);
                CachedSocket.cachedKeys.remove(senderid);
                System.out.println("心跳包15秒没有响应，关闭用户" + senderid + "的通道");
                break;
            }
        }
        return isOnline;
    }

    /**
     * 通过对request和socket进行处理
     *
     * @param request
     * @param socket
     */
    private void handleRequest(RequestModel request, SocketChannel socket) {
        Processor processor = new DefaultProcessor();
        ResponseModel response = processor.createResponse();
        processor.service(request, response, socket);
    }
}