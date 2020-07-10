package com.linfengda.sb.support.cache.handler.strategy.impl;

import com.linfengda.sb.support.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.cache.entity.type.CachePolicy;
import com.linfengda.sb.support.cache.handler.strategy.CacheStrategy;
import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.manager.RedisTemplateHolder;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

/**
 * 描述: object
 *
 * @author: linfengda
 * @date: 2020-07-08 16:17
 */
public class ObjCacheStrategy implements CacheStrategy {

    @Override
    public Object getCache(CacheParamDTO param) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        Object value = jacksonRedisTemplate.getObject(key);
        return value;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        JacksonRedisTemplate jacksonRedisTemplate = RedisTemplateHolder.getRedisTemplate();
        String key = CacheParamBuilder.INSTANCE.buildObjectKey(param);
        if (Constant.NO_EXPIRE_TIME.equals(param.getTimeOut())) {
            jacksonRedisTemplate.setObject(key, value);
        }else {
            if (param.getPolicies().contains(CachePolicy.PREVENT_CACHE_SNOW_SLIDE)) {

            }
            jacksonRedisTemplate.setObject(key, value, param.getTimeOut(), param.getTimeUnit());
        }
    }
}
