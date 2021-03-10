package com.lfd.srv.demo.support.redis.cache.handler.impl;

import com.lfd.srv.demo.support.redis.cache.resolver.CacheDataTypeResolver;
import com.lfd.srv.demo.support.redis.cache.resolver.CacheDataTypeResolverHolder;
import com.lfd.srv.demo.support.redis.cache.entity.bo.CacheResultBO;
import com.lfd.srv.demo.support.redis.cache.entity.dto.CacheParamDTO;
import com.lfd.srv.demo.support.redis.cache.entity.dto.CacheTargetDTO;
import com.lfd.srv.demo.support.redis.cache.entity.type.CacheAnnotationType;
import com.lfd.srv.demo.support.redis.cache.handler.AbstractCacheHandler;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @description 查询缓存注解处理 handler
 * @author linfengda
 * @date 2020-07-26 14:08
 */
@Slf4j
public class QueryCacheHandler extends AbstractCacheHandler {

    @Override
    public boolean support(CacheAnnotationType annotationType) {
        return CacheAnnotationType.QUERY == annotationType;
    }

    @Override
    public Object doCache(CacheTargetDTO cacheTargetDTO) throws Throwable {
        log.debug("查询缓存注解处理，dataType={}", cacheTargetDTO.getParam().getDataType());
        CacheParamDTO param = cacheTargetDTO.getParam();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(param.getDataType());
        CacheResultBO resultBO = resolver.getCache(param);
        if (resultBO.getHasKey()) {
            return resultBO.getTarget();
        }
        Object value;
        if (!param.getPreCacheHotKeyMultiLoad()) {
            return getMethodResult(cacheTargetDTO);
        }
        try {
            if (!redisDistributedLock.tryLock(param.getLockKey())) {
                // 出现并行加载的情况，尝试自旋读取缓存，超出最大自旋时间仍然从DB加载
                int spinCount = 1;
                while (true) {
                    try {
                        if (param.getMaxSpinCount() < spinCount) {
                            break;
                        }
                        log.info("尝试自旋读取缓存，线程id：{}", Thread.currentThread().getId());
                        Thread.sleep(param.getSpinTime());
                        value = resolver.getCache(param);
                        if (null != value) {
                            return value;
                        }
                        if (resolver.hasKey(param)) {
                            return null;
                        }
                        spinCount++;
                    } catch (InterruptedException e) {
                        log.error("等待缓存加载自旋失败！", e);
                        break;
                    }
                }
            }
            log.info("缓存查询不到，尝试从DB查询，线程id：{}", Thread.currentThread().getId());
            long t1 = System.currentTimeMillis();
            value = getMethodResult(cacheTargetDTO);
            log.info("尝试从DB查询，尝试从DB查询，线程id：{}，耗时：{}", Thread.currentThread().getId(), System.currentTimeMillis()-t1);
            return value;
        }finally {
            redisDistributedLock.unLock(param.getLockKey());
        }
    }

    /**
     * 执行方法
     * @param cacheTargetDTO    对象
     * @return                  方法返回值
     * @throws Throwable
     */
    private Object getMethodResult(CacheTargetDTO cacheTargetDTO) throws Throwable {
        MethodInvocation invocation = cacheTargetDTO.getInvocation();
        Object value = invocation.proceed();
        CacheDataTypeResolver resolver = CacheDataTypeResolverHolder.INSTANCE.getResolver(cacheTargetDTO.getParam().getDataType());
        resolver.setCache(cacheTargetDTO.getParam(), value);
        return value;
    }
}
