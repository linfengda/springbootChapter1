package com.lfd.soa.srv.demo.support.queue.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description 注解执行动态代理
 * @author linfengda
 * @date 2021-01-15 11:54
 */
public class QueueServiceDynamicProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
