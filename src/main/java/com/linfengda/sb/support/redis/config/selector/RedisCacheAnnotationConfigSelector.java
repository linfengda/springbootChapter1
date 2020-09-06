package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.RedisCacheInitializer;
import com.linfengda.sb.support.redis.config.RedisCacheAnnotationConfig;
import com.linfengda.sb.support.redis.config.RedisConfig;
import com.linfengda.sb.support.redis.config.annotation.EnableRedisCacheAnnotation;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 开启redis缓存注解
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class RedisCacheAnnotationConfigSelector extends AdviceModeImportSelector<EnableRedisCacheAnnotation> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {RedisConfig.class.getName(), RedisCacheAnnotationConfig.class.getName(), RedisCacheInitializer.class.getName()};
    }
}
