package com.linfengda.sb.chapter1.common.proxy.jdk;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-12-24 15:21
 */
public class RealProxyTarget implements ProxyTarget {
    @Override
    public void doSomething() {
        System.out.println("call doSomething()");
    }
}
