package com.netease.corp.hzxiejiayun.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 通用的IO存储器
 */
public class CommonWriter {

    /**
     * 将可以序列化的对象转换为ByteBuffer的形式
     *
     * @param obj
     * @return
     */
    public static ByteBuffer setObject(Serializable obj) {
        ByteBuffer result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] tmp = baos.toByteArray();
            result = ByteBuffer.wrap(tmp);
            baos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}