package com.linfengda.sb.support.cache.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 缓存配置类引入selector
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class CacheConfigSelector extends AdviceModeImportSelector<EnableCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {CacheConfig.class.getName(), RedisConfig.class.getName(), RedisSupportHolder.class.getName()};
    }
}
