package com.linfengda.sb.chapter1.common.proxy.jdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述: jdk代理handler
 *
 * @author linfengda
 * @create 2019-12-24 15:22
 */
@Slf4j
@AllArgsConstructor
public class JdkProxyHandler implements InvocationHandler {
    /**
     * 代理目标
     */
    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("日志打印方法执行，time={}", System.currentTimeMillis());
        Object result = method.invoke(target,args);
        return result;
    }
}
