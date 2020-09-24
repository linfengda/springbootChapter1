package com.linfengda.sb.support.redis.cache.interceptor;

import com.linfengda.sb.support.redis.cache.builder.CacheMethodMetaBuilder;
import com.linfengda.sb.support.redis.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.handler.CacheHandler;
import com.linfengda.sb.support.redis.cache.handler.CacheHandlerHolder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 提供对
 * {@link com.linfengda.sb.support.redis.cache.annotation.QueryCache},
 * {@link com.linfengda.sb.support.redis.cache.annotation.DeleteCache},
 * {@link com.linfengda.sb.support.redis.cache.annotation.UpdateCache}
 * 注解的支持
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public class CacheMethodHandlerAdapter {

    /**
     * Actually invoke the handler
     * @param invocation
     * @param annotationType
     * @return
     * @throws Throwable
     */
    protected Object invokeCacheMethod(MethodInvocation invocation, CacheAnnotationType annotationType) throws Throwable {
        Method method = invocation.getMethod();
        CacheMethodMeta cacheMethodMeta = CacheMethodMetaBuilder.getCacheMethodMeta(method, annotationType);
        CacheTargetDTO cacheTargetDTO;
        if (CacheAnnotationType.DELETE == cacheMethodMeta.getCacheAnnotationType()) {
            cacheTargetDTO = getDeleteCacheTargetDTO(invocation, cacheMethodMeta);
        }else {
            cacheTargetDTO = getCacheTargetDTO(invocation, cacheMethodMeta);
        }
        CacheHandler handler = CacheHandlerHolder.INSTANCE.getHandler(annotationType);
        return handler.doCache(cacheTargetDTO);
    }

    private CacheTargetDTO getDeleteCacheTargetDTO(MethodInvocation invocation, CacheMethodMeta cacheMethodMeta) {
        CacheTargetDTO cacheTargetDTO = new CacheTargetDTO();
        cacheTargetDTO.setInvocation(invocation);
        List<CacheParamDTO> deleteParams = new ArrayList<>();
        for (CacheMethodMeta deleteMeta : cacheMethodMeta.getDeleteMetas()) {
            CacheParamDTO param = CacheParamBuilder.INSTANCE.initCacheParam(deleteMeta, invocation.getArguments());
            deleteParams.add(param);
        }
        cacheTargetDTO.setDeleteParams(deleteParams);
        return cacheTargetDTO;
    }

    private CacheTargetDTO getCacheTargetDTO(MethodInvocation invocation, CacheMethodMeta cacheMethodMeta) {
        CacheTargetDTO cacheTargetDTO = new CacheTargetDTO();
        cacheTargetDTO.setInvocation(invocation);
        CacheParamDTO param = CacheParamBuilder.INSTANCE.initCacheParam(cacheMethodMeta, invocation.getArguments());
        cacheTargetDTO.setParam(param);
        return cacheTargetDTO;
    }
}
