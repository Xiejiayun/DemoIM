package com.netease.corp.hzxiejiayun.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by hzxiejiayun on 2016/6/20.
 */
public class CommonWriter {

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
