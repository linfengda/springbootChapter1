package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.AnnotationAttributeHolder;
import com.linfengda.sb.support.redis.config.annotation.EnableRedis;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

/**
 * @description
 * @author linfengda
 * @date 2020-09-13 23:29
 */
public abstract class AbstractRedisImportSelector <A extends Annotation> implements ImportSelector {
    public static final String ENABLE_CACHE_ANNOTATION_ATTRIBUTE_NAME = "enableCacheAnnotation";
    public static final String ENABLE_BUSINESS_LOCK_ANNOTATION_ATTRIBUTE_NAME = "enableBusinessLockAnnotation";
    protected AnnotationAttributes attributes;

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableRedis.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException("@EnableRedis is not present on importing class " + importMetadata.getClassName());
        }
        AnnotationAttributeHolder.INSTANCE.init(this.attributes);
        boolean enableCacheAnnotation = attributes.getBoolean(ENABLE_CACHE_ANNOTATION_ATTRIBUTE_NAME);
        boolean enableBusinessLockAnnotation = attributes.getBoolean(ENABLE_BUSINESS_LOCK_ANNOTATION_ATTRIBUTE_NAME);
        return selectImports(enableCacheAnnotation, enableBusinessLockAnnotation);
    }

    /**
     * 根据属性初始化redis配置
     * @param enableCacheAnnotation         是否启用缓存注解
     * @param enableBusinessLockAnnotation  是否启用业务锁注解
     * @return
     */
    protected abstract String[] selectImports(boolean enableCacheAnnotation, boolean enableBusinessLockAnnotation);
}
