package test.com.netease.corp.hzxiejiayun.common.util;

import com.netease.corp.hzxiejiayun.common.io.CommonReader;
import com.netease.corp.hzxiejiayun.common.io.CommonWriter;
import com.netease.corp.hzxiejiayun.common.model.RequestModel;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzxiejiayun on 2016/6/20.
 */
public class Test {
    public static void main(String[] args) {
        RequestModel requestModel = new RequestModel();
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("username", "jack");
        extras.put("password", "tom");
        requestModel.setExtras(extras);
        ByteBuffer buff = CommonWriter.setObject(requestModel);
        System.out.println(buff.toString());
        Object obj = CommonReader.getObject(buff);
        System.out.println(obj.toString());
        RequestModel model = (RequestModel) obj;
        System.out.println(obj.toString());
    }
}
