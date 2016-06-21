package com.netease.corp.hzxiejiayun.common.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * Created by hzxiejiayun on 2016/6/20.
 * <p/>
 * 通用的IO读取器
 */
public class CommonReader {
    
    /**
     * 从ByteBuffer中读取对应的序列化对象
     *
     * @param byteBuffer
     * @return
     */
    public static Object getObject(ByteBuffer byteBuffer) {
        Object obj = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            bais.close();
            ois.close();
            byteBuffer.clear();
            return obj;
        } catch (IOException e) {
            System.out.println("发生错误"+ e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
