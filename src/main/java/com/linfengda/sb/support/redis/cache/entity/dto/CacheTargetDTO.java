package com.linfengda.sb.support.redis.cache.entity.dto;

import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: redis缓存处理对象
 *
 * @author: linfengda
 * @date: 2020-07-06 10:46
 */
@Data
public class CacheTargetDTO {
    /**
     * 方法代理
     */
    private MethodInvocation invocation;
    /**
     * 缓存参数信息
     */
    private CacheParamDTO param;
}
