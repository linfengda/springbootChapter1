package com.linfengda.sb.chapter1.common.proxy.jdk;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述: 权限校验JDK动态代理
 *
 * @author linfengda
 * @create 2020-03-30 16:32
 */
@Slf4j
@AllArgsConstructor
public class AuthCheckDynamicProxy implements InvocationHandler {
    /**
     * 代理目标
     */
    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("权限校验方法执行，time={}", System.currentTimeMillis());
        Object result = method.invoke(target,args);
        return result;
    }
}
