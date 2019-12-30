package com.linfengda.sb.chapter1.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-24 15:22
 */
public class ProxyHandler implements InvocationHandler {
    private Object tar;

    public Object bind(Object tar) {
        this.tar = tar;
        InvocationHandler invocationHandler = this;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), invocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start time:"+System.currentTimeMillis());
        Object result = method.invoke(tar,args);
        System.out.println("end time:"+System.currentTimeMillis());
        return result;
    }
}
