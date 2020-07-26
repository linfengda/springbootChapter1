package com.linfengda.sb.support.redis.cache.entity.meta;

import com.linfengda.sb.support.redis.cache.entity.type.CacheExtraStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
     * 数据类型
     */
    private DataType dataType;
    /**
     * 缓存前缀
     */
    private String prefix;
    /**
     * 缓存失效时间
     */
    private Long timeOut;
    /**
     * 缓存失效时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 缓存策略
     */
    private List<CacheExtraStrategy> strategies;
    /**
     * 缓存最大数量淘汰策略
     */
    private CacheMaxSizeStrategy maxSizeStrategy;
    /**
     * 最大缓存数量
     */
    private Long maxSize;
    /**
     * 是否删除前缀的所有缓存
     * @return
     */
    private Boolean allEntries;
    /**
     * 参数列表
     */
    private List<CacheKeyMeta> keyMetas;

    @Data
    public static class CacheKeyMeta {
        /**
         * 参数
         */
        private Parameter parameter;
        /**
         * 参数下标
         */
        private Integer index;
        /**
         * key为空时使用值
         */
        String nullKey;
    }
}
