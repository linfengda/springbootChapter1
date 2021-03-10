package com.lfd.srv.demo.support.redis.cache.entity.dto;

import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

/**
 * 描述: redis缓存处理对象
 *
 * @author linfengda
 * @date 2020-07-26 10:46
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
    /**
     * 删除缓存参数信息（多个）
     */
    private List<CacheParamDTO> deleteParams;
}
