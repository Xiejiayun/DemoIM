package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.RequestResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/16.
 */
public interface ProtocolParser {

    void parseHeader(RequestResponseModel model);

    void parseContent(RequestResponseModel model);

}
