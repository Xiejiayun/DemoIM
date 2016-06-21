package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.BaseModel;
import com.netease.corp.hzxiejiayun.common.model.RequestResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/16.
 * <p/>
 * 协议解析器，对传输的对象进行解析
 */
public interface ProtocolParser {

    /**
     * 将请求响应对象解析成BaseMode对象
     *
     * @param model 对象模型
     * @return BaseModel
     */
    BaseModel parse(RequestResponseModel model);
}