package com.linfengda.sb.support.cache.manager;

import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;

/**
 * 描述: 获取redis操作摸板
 *
 * @author: linfengda
 * @date: 2020-07-07 16:47
 */
public class RedisTemplateHolder {

    /**
     * 获取redisTemplate
     * @return  redisTemplate
     */
    public static JacksonRedisTemplate getRedisTemplate() {
        JacksonRedisTemplate jacksonRedisTemplate = SpringUtil.getBean(JacksonRedisTemplate.class);
        return jacksonRedisTemplate;
    }
}
