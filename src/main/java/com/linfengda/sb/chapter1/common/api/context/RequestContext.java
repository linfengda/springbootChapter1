package com.linfengda.sb.chapter1.common.api.context;

import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;

/**
 * 描述: 请求信息上下文
 *
 * @author linfengda
 * @create 2018-08-19 22:46
 */
public class RequestContext {
    private final static ThreadLocal<RequestInfoBO> REQUEST_PARAM_THREAD_LOCAL = new ThreadLocal();

    public static void setParam(RequestInfoBO requestInfoBO){
        REQUEST_PARAM_THREAD_LOCAL.set(requestInfoBO);
    }

    public static RequestInfoBO getParams(){
        return REQUEST_PARAM_THREAD_LOCAL.get();
    }

    public static void remove(){
        REQUEST_PARAM_THREAD_LOCAL.remove();
    }
}
