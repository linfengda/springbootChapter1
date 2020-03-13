package com.linfengda.sb.chapter1.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述: JDK动态代理
 *
 * @author linfengda
 * @create 2019-12-24 15:22
 */
public class ProxyHandler implements InvocationHandler {
    private Object target;

    public Object bind(Object tar) {
        this.target = tar;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start time:"+System.currentTimeMillis());
        Object result = method.invoke(target,args);
        System.out.println("end time:"+System.currentTimeMillis());
        return result;
    }
}
