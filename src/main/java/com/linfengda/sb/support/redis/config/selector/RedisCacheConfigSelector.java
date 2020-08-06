package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.cache.manager.CacheBackgroundManager;
import com.linfengda.sb.support.redis.config.RedisCacheConfig;
import com.linfengda.sb.support.redis.config.RedisConfig;
import com.linfengda.sb.support.redis.config.RedisSupportConfig;
import com.linfengda.sb.support.redis.config.annotation.EnableRedisCache;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 缓存注解相关配置引入
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class RedisCacheConfigSelector extends AdviceModeImportSelector<EnableRedisCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {RedisConfig.class.getName(), RedisCacheConfig.class.getName(), RedisSupportConfig.class.getName(), CacheBackgroundManager.class.getName()};
    }
}
