package com.linfengda.sb.support.gateway.session;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.stereotype.Component;

/**
 * @description: 访问api接口缓存，通过com.linfengda.sb.support.gateway.interceptor.impl.ApiCallInterceptor后初始化
 * @author: linfengda
 * @date: 2020-12-20 20:12
 */
@Component
public class RequestSessionHelper {
    private final static ThreadLocal<RequestInfoBO> MES_SESSION_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void put(RequestInfoBO session){
        MES_SESSION_INFO_THREAD_LOCAL.set(session);
    }

    public static void remove() {
        MES_SESSION_INFO_THREAD_LOCAL.remove();
    }

    public static RequestInfoBO get() {
        return MES_SESSION_INFO_THREAD_LOCAL.get();
    }
}
