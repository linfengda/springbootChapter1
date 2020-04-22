package com.linfengda.sb.chapter1.common.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 描述: JDK动态代理提供者
 *
 * @author linfengda
 * @create 2020-03-30 16:22
 */
public class ProxyHandlerProvider {

    /**
     * 提供一个日志打印JDK动态代理
     * @param target    代理目标类
     * @return
     */
    public static Object newLogDynamicProxy(Object target) {
        LogDynamicProxy logDynamicProxy = new LogDynamicProxy(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), logDynamicProxy);
    }

    /**
     * 提供一个权限校验JDK动态代理
     * @param target    代理目标类
     * @return
     */
    public static Object newAuthCheckDynamicProxy(Object target) {
        AuthCheckDynamicProxy authCheckDynamicProxy = new AuthCheckDynamicProxy(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), authCheckDynamicProxy);
    }
}
