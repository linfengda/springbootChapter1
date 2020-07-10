
package com.linfengda.sb.support.cache.builder;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.KeyBaseType;
import com.linfengda.sb.support.cache.exception.BusinessException;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 缓存key封装
 *
 * @author: linfengda
 * @date: 2020-07-06 15:12
 */
public enum CacheParamBuilder {
    /**
     * builder
     */
    INSTANCE;

    /**
     * 初始化缓存参数DTO
     * @param cacheMethodMeta   缓存信息
     * @param arguments         参数列表
     * @param invocation        方法代理
     */
    public CacheParamDTO initCacheParam(CacheMethodMeta cacheMethodMeta, Object[] arguments, MethodInvocation invocation) {
        // 初始化缓存前缀，过期策略
        CacheParamDTO cacheParamDTO = new CacheParamDTO();
        cacheParamDTO.setInvocation(invocation);
        cacheParamDTO.setDataType(cacheMethodMeta.getDataType());
        cacheParamDTO.setPrefix(StringUtils.isEmpty(cacheMethodMeta.getPrefix()) ? cacheMethodMeta.getMethodName() : cacheMethodMeta.getPrefix());
        cacheParamDTO.setTimeOut(cacheMethodMeta.getTimeOut());
        cacheParamDTO.setTimeUnit(cacheMethodMeta.getTimeUnit());
        cacheParamDTO.setPolicies(cacheMethodMeta.getPolicies());
        cacheParamDTO.setMaxSize(cacheMethodMeta.getMaxSize());
        cacheParamDTO.setAllEntries(cacheMethodMeta.getAllEntries());
        List<CacheMethodMeta.CacheKeyMeta> keyMetas = cacheMethodMeta.getKeyMetas();
        if (CollectionUtils.isEmpty(keyMetas)) {
            return cacheParamDTO;
        }
        if (null == arguments || 0 == arguments.length) {
            return cacheParamDTO;
        }
        // 初始化缓存key
        List<String> keys = new ArrayList<>();
        for (CacheMethodMeta.CacheKeyMeta keyMeta : keyMetas) {
            Object argument = arguments[keyMeta.getIndex()];
            if (!KeyBaseType.isBaseType(argument.getClass().getName())) {
                throw new BusinessException("不支持的缓存key参数类型：" + argument.getClass().getName());
            }
            if (null == argument) {
                keys.add(keyMeta.getNullKey());
                continue;
            }
            keys.add(String.valueOf(argument));
        }
        return cacheParamDTO;
    }

    /**
     * 获取object类型缓存key
     * @param param 缓存参数
     * @return      缓存key
     */
    public String buildObjectKey(CacheParamDTO param) {
        StringBuilder builder = new StringBuilder();
        builder.append(param.getPrefix());
        if (CollectionUtils.isEmpty(param.getKeys())) {
            return builder.toString();
        }
        builder.append(getKey(param.getKeys()));
        return builder.toString();
    }

    /**
     * 获取hash类型缓存key
     * @param param 缓存参数
     * @return      缓存key
     */
    public HashKey buildHashKey(CacheParamDTO param) {
        HashKey hashKey = new HashKey();
        hashKey.setKey(param.getPrefix());
        if (CollectionUtils.isEmpty(param.getKeys())) {
            return hashKey;
        }
        hashKey.setHashKey(getKey(param.getKeys()));
        return hashKey;
    }

    private String getKey(List<String> keys) {
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            builder.append(Constant.COLON + key);
        }
        return builder.toString();
    }
}