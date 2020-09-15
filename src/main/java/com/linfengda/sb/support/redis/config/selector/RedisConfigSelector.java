package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.RedisCacheAnnotationConfig;
import com.linfengda.sb.support.redis.config.RedisConfig;

/**
 * @description: 开启redis
 * @author: linfengda
 * @date: 2020-07-26 22:36
 */

public class RedisConfigSelector extends AbstractRedisImportSelector {

    @Override
    protected String[] selectImports(Boolean openCacheAnnotation) {
        if (true == openCacheAnnotation) {
            return new String[] {RedisConfig.class.getName(), RedisCacheAnnotationConfig.class.getName()};
        }else {
            return new String[] {RedisConfig.class.getName()};
        }
    }
}
