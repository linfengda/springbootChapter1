package com.linfengda.sb.support.queue.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @description: 代理类型
 * @author: linfengda
 * @date: 2021-01-15 11:52
 */
public class QueueServiceProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    public QueueServiceProxyFactoryBean(Class<T> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.interfaceClass);
        enhancer.setCallback(new QueueServiceDynamicProxy());
        return (T) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
