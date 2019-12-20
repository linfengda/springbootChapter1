package com.linfengda.sb.chapter1.common.api.context;

import com.linfengda.sb.chapter1.common.api.entity.RequestParam;

/**
 * 描述: 请求参数上下文
 *
 * @author linfengda
 * @create 2018-08-19 22:46
 */
public class RequestParamContext {
    private final static ThreadLocal<RequestParam> REQUEST_PARAM_THREAD_LOCAL = new ThreadLocal();

    public static void setParam(RequestParam requestParam){
        REQUEST_PARAM_THREAD_LOCAL.set(requestParam);
    }

    public static RequestParam getParams(){
        return REQUEST_PARAM_THREAD_LOCAL.get();
    }

    public static void remove(){
        REQUEST_PARAM_THREAD_LOCAL.remove();
    }
}
