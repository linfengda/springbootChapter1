package com.lfd.srv.demo.demo.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 描述: JDK代理提供者
 *
 * @author linfengda
 * @create 2020-03-30 16:22
 */
public class JdkProxyHandlerProvider {

    /**
     * 返回jdk的代理
     * @param target    代理目标类
     * @return
     */
    public static Object newProxy(Object target) {
        JdkProxyHandler jdkProxyHandler = new JdkProxyHandler(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), jdkProxyHandler);
    }
}
