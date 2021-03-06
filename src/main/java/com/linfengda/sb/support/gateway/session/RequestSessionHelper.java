package com.linfengda.sb.support.gateway.session;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.linfengda.sb.support.gateway.entity.RequestInfoBO;
import org.springframework.stereotype.Component;

/**
 * @description 访问api接口缓存，通过com.linfengda.sb.support.gateway.interceptor.impl.ApiCallInterceptor后初始化
 * @author linfengda
 * @date 2020-12-20 20:12
 */
@Component
public class RequestSessionHelper {
    private final static ThreadLocal<RequestInfoBO> REQUEST_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void put(RequestInfoBO session){
        REQUEST_INFO_THREAD_LOCAL.set(session);
    }

    public static void remove() {
        REQUEST_INFO_THREAD_LOCAL.remove();
    }

    public static RequestInfoBO get() {
        return REQUEST_INFO_THREAD_LOCAL.get();
    }
}
