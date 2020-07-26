package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.RedisConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: redis相关配置引入
 * @author: linfengda
 * @date: 2020-07-26 22:36
 */
public class RedisConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {RedisConfig.class.getName()};
    }
}
