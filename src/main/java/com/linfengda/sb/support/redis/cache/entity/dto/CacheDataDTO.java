package com.linfengda.sb.support.redis.cache.entity.dto;

import com.linfengda.sb.support.redis.cache.entity.type.OperationType;
import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 描述: 缓存数据DTO
 *
 * @author: linfengda
 * @date: 2020-07-06 10:46
 */
@Data
public class CacheDataDTO {
    /**
     * 方法代理
     */
    private MethodInvocation invocation;
    /**
     * 缓存参数信息
     */
    private CacheParamDTO param;
    /**
     * 缓存操作类型
     */
    private OperationType type;
}
