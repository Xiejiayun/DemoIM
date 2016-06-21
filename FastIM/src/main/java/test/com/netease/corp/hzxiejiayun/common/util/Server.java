package test.com.netease.corp.hzxiejiayun.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hzxiejiayun on 2016/6/21.
 */


public class Server {
    private Selector selector = null;
    private ServerSocketChannel ssc = null;
    //服务器端通信端口号
    private int port = 6666;
    //在线用户列表
    private Hashtable<String, SocketChannel> userList = null;

    public Server() {
    }

    public Server(int port) {
        this.port = port;
    }

    public static void main(String args[]) {
        Server server = new Server();
        server.init();
        server.start();
    }

    //初始化服务器
    public void init() {
        try {
            //创建选择器对象
            selector = Selector.open();
            //创建ServerSocketChannel
            ssc = ServerSocketChannel.open();
            //设置ServerSocketChannel为非阻塞模式
            ssc.configureBlocking(false);
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("主机地址 --------> " + ip);
            InetSocketAddress isa = new InetSocketAddress(ip, port);
            //将与本通道相关的服务器套接字对象绑定到指定地址和端口
            ssc.socket().bind(isa);
            //创建在线用户列表
            userList = new Hashtable<String, SocketChannel>();
        } catch (IOException e) {
            System.out.println("初始化服务器时异常，原因  -------->  " + e.getMessage());
        }
    }

    //启动服务器
    public void start() {
        try {
            //将ServerSocketChannel注册到Selector上，准备接收新连接请求
            SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
            SocketChannel sc;
            int n;
            String name; //用户名
            String msg; //用户发言信息
            while (true) {
                //选择当前所有处于就绪状态的通道所对应的选择键,并将这些键组成已选择键集
                n = selector.select(); //n为已选择键集中键的个数
                if (n > 0) {
                    //获取此选择器的已选择键集。
                    Set readyKeys = selector.selectedKeys();
                    Iterator it = readyKeys.iterator();
                    //遍历当前已选择键集
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();
                        //从当前已选择键集中移除当前键，避免重复处理
                        it.remove();
                        //如果当前键对应的通道已准备好接受新的套接字连接
                        if (key.isAcceptable()) {
                            //获取当前键对应的可选择通道（ServerSocketChannel）
                            ssc = (ServerSocketChannel) key.channel();
                            //接收新的套接字连接请求，返回新建的SocketChannel
                            sc = (SocketChannel) ssc.accept();
                            //如果有新用户接入
                            if (sc != null) {
                                //接收新上线用户姓名
                                name = readMessage(sc);
                                //设置新建的SocketChannel为非阻塞模式
                                sc.configureBlocking(false);
                                //将新建的SocketChannel注册到Selector上，准备进行数据"写"操作，
                                //并将当前用户名以附件的方式附带记录到新建的选择键上。
                                SelectionKey newKey = sc.register(selector, SelectionKey.OP_WRITE, name);
                                //将新上线用户信息加入到在线用户列表
                                userList.put(name, sc);
                                //发送"新用户上线"通知
                                transmitMessage(name + " in!", "--Server Info--");
                            }
                            //否则，如果当前键对应的通道已准备好进行"写"操作
                        } else if (key.isWritable()) {
                            //获取当前键对应的可选择通道（SocketChannel）
                            sc = (SocketChannel) key.channel();
                            //接收该通道相应用户的发言信息
                            msg = readMessage(sc);
                            //获取选择键上附带记录的当前用户名
                            name = key.attachment().toString();
                            //如果用户提出要下线
                            if (msg.equals("bye")) {
                                //从在线用户列表中移除当前用户
                                userList.remove(name);
                                //注销当前选择键对应的注册关系
                                key.cancel();
                                //关闭当前可选择通道
                                sc.close();
                                //发送"用户下线"通知
                                transmitMessage(name + " out!", "--Server Info--");
                                //否则，如果接收到的用户发言信息非空（""）
                            } else if (msg.length() > 0) {
                                //转发用户发言信息
                                transmitMessage(msg, name);
                            }
                        }
                    }
                }
                //延时循环，降低服务器端处理负荷
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("启动服务器时异常，原因  -------->  " + e.getMessage());
        }
    }

    //转发用户发言信息
    public void transmitMessage(String msg, String name) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap((name + ":" + msg).getBytes("UTF-8"));
            //将字节数组包装到缓冲区中
            Collection channels = userList.values();
            SocketChannel sc;
            for (Object o : channels) {
                sc = (SocketChannel) o;
                sc.write(buffer);
                //将缓冲区数据写入聊天面板(TextArea)
                buffer.flip();
                //将缓冲区ByteBuffer的极限值设置为当前数据实际大小，将缓冲区的值设置为0
            }
        } catch (Exception e) {
            System.out.println("转发用户发言信息时异常，原因  -------->  " + e.getMessage());
        }
    }

    //接收用户发言信息
    public String readMessage(SocketChannel sc) {
        String result = null;
        int n = 0;
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try {
            n = sc.read(buf);
            buf.flip();
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(buf);
            result = charBuffer.toString();
        } catch (IOException e) {
            System.out.println("接收用户发言信息时异常，原因  -------->  " + e.getMessage());
        }
        return result;
    }
}