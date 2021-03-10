package com.lfd.srv.demo.support.gateway.session;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.lfd.srv.demo.support.gateway.entity.RequestSessionBO;
import org.springframework.stereotype.Component;

/**
 * @description 访问api接口缓存，通过com.lfd.srv.demo.support.gateway.interceptor.impl.ApiCallInterceptor后初始化
 * @author linfengda
 * @date 2020-12-20 20:12
 */
@Component
public class RequestSession {
    private final static ThreadLocal<RequestSessionBO> REQUEST_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void put(RequestSessionBO session){
        REQUEST_INFO_THREAD_LOCAL.set(session);
    }

    public static void remove() {
        REQUEST_INFO_THREAD_LOCAL.remove();
    }

    public static RequestSessionBO get() {
        return REQUEST_INFO_THREAD_LOCAL.get();
    }
}
