package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.config.annotation.EnableRedisCacheAnnotation;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-08-18 19:08
 */
public class AbstractCacheConfig implements ImportAware {
    protected AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableRedisCacheAnnotation.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException(
                    "@EnableRedisCacheAnnotation is not present on importing class " + importMetadata.getClassName());
        }
    }
}
