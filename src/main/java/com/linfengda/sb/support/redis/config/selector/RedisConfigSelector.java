package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.RedisCacheAnnotationConfig;
import com.linfengda.sb.support.redis.config.RedisConfig;
import com.linfengda.sb.support.redis.config.BusinessLockAnnotationConfig;
import com.linfengda.sb.support.redis.config.annotation.EnableRedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 开启redis
 * @author linfengda
 * @date 2020-07-26 22:36
 */
public class RedisConfigSelector extends AbstractRedisImportSelector<EnableRedis> {

    @Override
    protected String[] selectImports(boolean enableCacheAnnotation, boolean enableBusinessLockAnnotation) {
        List<String> configList = new ArrayList<>();
        configList.add(RedisConfig.class.getName());
        if (enableCacheAnnotation) {
            configList.add(RedisCacheAnnotationConfig.class.getName());
        }
        if (enableBusinessLockAnnotation) {
            configList.add(BusinessLockAnnotationConfig.class.getName());
        }
        return configList.toArray(new String[0]);
    }
}
