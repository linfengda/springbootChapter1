
package com.linfengda.sb.support.cache.builder;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.meta.CacheKeyMeta;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 描述: 缓存key封装
 *
 * @author: linfengda
 * @date: 2020-07-06 15:12
 */
public enum CacheKeyBuilder {
    /**
     * builder
     */
    INSTANCE;

    /**
     * 获取object类型缓存key
     * @param meta  缓存方法信息
     * @return      缓存key
     */
    public String buildObjectKey(CacheMethodMeta meta) {
        StringBuilder builder = new StringBuilder();
        builder.append(meta.getPrefix());
        if (CollectionUtils.isEmpty(meta.getKeys())) {
            return builder.toString();
        }
        builder.append(getKey(meta));
        return builder.toString();
    }

    /**
     * 获取hash类型缓存key
     * @param meta  缓存方法信息
     * @return      缓存hashkey
     */
    public HashKey buildHashKey(CacheMethodMeta meta) {
        HashKey hashKey = new HashKey();
        hashKey.setMapName(meta.getPrefix());
        if (CollectionUtils.isEmpty(meta.getKeys())) {
            return hashKey;
        }
        hashKey.setHashKey(getKey(meta));
        return hashKey;
    }

    private String getKey(CacheMethodMeta meta) {
        StringBuilder builder = new StringBuilder();
        for (CacheKeyMeta keyMeta : meta.getKeys()) {
            if (!StringUtils.isEmpty(keyMeta.getCacheKey())) {
                builder.append(Constant.COLON + keyMeta.getCacheKey());
                continue;
            }
            if (Boolean.FALSE.equals(keyMeta.getNullable())) {
                builder.append(Constant.COLON + keyMeta.getNullKey());
            }
        }
        return builder.toString();
    }
}