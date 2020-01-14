package com.linfengda.sb.chapter1.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 描述: SpringBoot context提供者
 *
 * @author linfengda
 * @create 2020-01-14 14:04
 */
public class ApplicationContextHelper {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext ctx;

    public static void setCtx(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getCtx() {
        return ctx;
    }

    /**
     * 通过name获取 Bean.
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return getCtx().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getCtx().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getCtx().getBean(name, clazz);
    }
}
