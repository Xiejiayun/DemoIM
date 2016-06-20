package com.netease.corp.hzxiejiayun.common.protocol;

import com.netease.corp.hzxiejiayun.common.model.BaseModel;
import com.netease.corp.hzxiejiayun.common.model.RequestResponseModel;

/**
 * Created by hzxiejiayun on 2016/6/16.
 * <p/>
 * 协议包装器接口，对需要传输的对象使用协议进行包装
 */
public interface ProtocolWrapper {
    /**
     * 对基础对象进行包装
     * @param baseModel 基础对象
     * @return
     */
    RequestResponseModel wrap(BaseModel baseModel);
}
