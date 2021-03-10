package com.lfd.soa.srv.demo.demo.proxy.cglib;

/**
 * 描述: 被cglib代理的对象
 *
 * @author linfengda
 * @create 2020-03-27 13:40
 */
public class CglibProxyTarget {

    public String test(String input) {
        System.out.println("hello world");
        return "hello world";
    }
}
