package com.netease.corp.hzxiejiayun.server.io;

import java.net.ServerSocket;
import java.nio.CharBuffer;

/**
 * Created by hzxiejiayun on 2016/6/16.
 */
public class ServerReader {

    CharBuffer getSocketContext(ServerSocket serverSocket) {
        CharBuffer charBuffer = CharBuffer.allocate(1024);

        return charBuffer;
    }
}
