package com.linfengda.sb.support.redis.cache.entity.meta;

import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 描述: 缓存方法元数据
 *
 * @author linfengda
 * @create 2020-03-27 15:10
 */
@Data
public class CacheMethodMeta {
    /**
     * 原始方法对象
     */
    private Method method;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数列表
     */
    private List<CacheKeyMeta> methodCacheKeys;
    /**
     * 数据类型
     */
    private DataType dataType;
    /**
     * 缓存前缀
     */
    private String prefix;
    /**
     * 查询缓存参数
     */
    private CacheQueryMeta queryMeta;
    /**
     * 更新缓存参数
     */
    private CacheUpdateMeta updateMeta;
    /**
     * 删除缓存参数
     */
    private CacheDeleteMeta deleteMate;
}
