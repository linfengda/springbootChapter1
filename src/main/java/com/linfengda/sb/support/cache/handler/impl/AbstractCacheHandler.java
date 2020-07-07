package com.linfengda.sb.support.cache.handler.impl;

import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.support.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.cache.handler.CacheHandler;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.Data;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-07 10:32
 */
@Data
public abstract class AbstractCacheHandler implements CacheHandler {
    private CacheDataDTO cacheDataDTO;

    public AbstractCacheHandler(CacheDataDTO cacheDataDTO) {
        this.cacheDataDTO = cacheDataDTO;
    }

    /**
     * 获取redisTemplate
     * @return  redisTemplate
     */
    protected JacksonRedisTemplate getRedisTemplate() {
        JacksonRedisTemplate jacksonRedisTemplate = SpringUtil.getBean(JacksonRedisTemplate.class);
        return jacksonRedisTemplate;
    }
}
