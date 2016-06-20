package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.RequestResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/16.
 * <p/>
 * 协议解析器，对传输的对象进行解析
 */
public interface ProtocolParser {

    /**
     * 对传输的对象进行对象头解析
     *
     * @param model 对象模型
     */
    void parseHeader(RequestResponseModel model);

    /**
     * 对传输的对象进行对象内容解析
     *
     * @param model 对象模型
     */
    void parseContent(RequestResponseModel model);

}
