package com.linfengda.sb.support.cache.interceptor;

import com.linfengda.sb.support.cache.annotation.MapCacheDelete;
import com.linfengda.sb.support.cache.annotation.MapCacheUpdate;
import com.linfengda.sb.support.cache.annotation.ObjCacheDelete;
import com.linfengda.sb.support.cache.annotation.ObjCacheUpdate;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述: 拦截{@link ObjCacheUpdate}，{@link ObjCacheDelete}，{@link MapCacheUpdate}，{@link MapCacheDelete}注解的增强器
 *
 * @author linfengda
 * @create 2020-03-26 11:30
 */
public class UpdateCacheMethodPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    /**
     * 缓存注解静态切入点
     */
    private final UpdateCacheMethodPointcut pointcut = new UpdateCacheMethodPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
