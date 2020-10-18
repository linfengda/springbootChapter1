
package com.linfengda.sb.support.redis.cache.builder;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.HashKey;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheKeyMeta;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 缓存key封装
 *
 * @author: linfengda
 * @date: 2020-07-24 15:12
 */
public enum CacheParamBuilder {
    /**
     * builder
     */
    INSTANCE;

    /**
     * 初始化缓存参数DTO
     * @param cacheMethodMeta   方法缓存信息
     * @param arguments         方法参数列表
     */
    public CacheParamDTO initCacheParam(CacheMethodMeta cacheMethodMeta, Object[] arguments) {
        // 初始化缓存前缀，过期策略
        CacheParamDTO cacheParamDTO = new CacheParamDTO();
        cacheParamDTO.setReturnType(cacheMethodMeta.getMethod().getReturnType());
        cacheParamDTO.setDataType(cacheMethodMeta.getDataType());
        cacheParamDTO.setPrefix(StringUtils.isEmpty(cacheMethodMeta.getPrefix()) ? cacheMethodMeta.getMethodName() : cacheMethodMeta.getPrefix());
        BeanUtils.copyProperties(cacheMethodMeta, cacheParamDTO);
        // 初始化缓存key
        List<String> keys = parseKeys(cacheMethodMeta.getMethodCacheKeys(), arguments);
        cacheParamDTO.setKey(buildKey(cacheParamDTO.getPrefix(), keys));
        if (DataType.HASH == cacheMethodMeta.getDataType()) {
            cacheParamDTO.setHashKey(buildHashKey(cacheParamDTO.getPrefix(), keys));
        }
        cacheParamDTO.setLruKey(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.COLON + cacheParamDTO.getPrefix());
        cacheParamDTO.setLockKey(Constant.DEFAULT_LOCK_PREFIX + Constant.COLON + cacheParamDTO.getKey());
        return cacheParamDTO;
    }

    /**
     * 初始化缓存key列表
     * @param keyMetas  缓存方法key列表
     * @param arguments 缓存方法参数列表
     * @return          缓存key列表
     */
    private List<String> parseKeys(List<CacheKeyMeta> keyMetas, Object[] arguments) {
        List<String> keys = new ArrayList<>();
        if (CollectionUtils.isEmpty(keyMetas)) {
            return keys;
        }
        if (null == arguments || 0 == arguments.length) {
            return keys;
        }
        for (CacheKeyMeta keyMeta : keyMetas) {
            Object argument = arguments[keyMeta.getIndex()];
            if (null == argument) {
                keys.add(keyMeta.getNullKey());
                continue;
            }
            keys.add(argument.toString());
        }
        return keys;
    }

    /**
     * 获取object类型缓存key
     * @param prefix    key前缀
     * @param keys      key列表
     * @return          缓存key
     */
    private String buildKey(String prefix, List<String> keys) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        if (CollectionUtils.isEmpty(keys)) {
            return builder.toString();
        }
        for (String key : keys) {
            builder.append(Constant.COLON + key);
        }
        return builder.toString();
    }

    /**
     * 获取hash类型缓存key
     * @param prefix    key前缀
     * @param keys      key列表
     * @return          缓存hashKey
     */
    private HashKey buildHashKey(String prefix, List<String> keys) {
        HashKey hashKey = new HashKey();
        hashKey.setKey(prefix);
        if (CollectionUtils.isEmpty(keys)) {
            hashKey.setHashKey("");
            return hashKey;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (i == 0) {
                builder.append(key);
            }else {
                builder.append(Constant.COLON + key);
            }
        }
        hashKey.setHashKey(builder.toString());
        return hashKey;
    }
}