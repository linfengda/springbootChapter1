package com.lfd.soa.srv.demo.demo.proxy.cglib;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 描述: Cglib动态代理测试
 *
 * @author linfengda
 * @create 2020-03-27 13:42
 */
public class CglibProxyTest {

    /**
     * cglib代理实现-无操作
     */
    @Test
    public void test1() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibProxyTarget.class);
        enhancer.setCallback(NoOp.INSTANCE);
        CglibProxyTarget proxy = (CglibProxyTarget)enhancer.create();
        assertThat(proxy.test(null), CoreMatchers.equalTo("hello world"));
    }

    /**
     * cglib代理实现-返回固定值
     */
    @Test
    public void test2() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibProxyTarget.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "hello cglib!";
            }
        });
        CglibProxyTarget proxy = (CglibProxyTarget)enhancer.create();
        assertThat(proxy.test(null), CoreMatchers.equalTo("hello cglib!"));
    }

    /**
     * cglib代理实现-方法拦截
     */
    @Test
    public void test3() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibProxyTarget.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after");
                return result;
            }
        });
        CglibProxyTarget proxy = (CglibProxyTarget)enhancer.create();
        assertThat(proxy.test(null), CoreMatchers.equalTo("hello world"));
    }
}
