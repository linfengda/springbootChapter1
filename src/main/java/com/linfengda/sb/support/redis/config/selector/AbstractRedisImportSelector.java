package com.linfengda.sb.support.redis.config.selector;

import com.linfengda.sb.support.redis.config.meta.AnnotationMetaHolder;
import com.linfengda.sb.support.redis.config.annotation.EnableRedis;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

/**
 * @description:
 * @author: linfengda
 * @date: 2020-09-13 23:29
 */
public abstract class AbstractRedisImportSelector <A extends Annotation> implements ImportSelector {
    public static final String OPEN_CACHE_ANNOTATION_ATTRIBUTE_NAME = "openCacheAnnotation";
    protected AnnotationAttributes attributes;

    @Override
    public final String[] selectImports(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableRedis.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException("@EnableRedis is not present on importing class " + importMetadata.getClassName());
        }
        Boolean openCacheAnnotation = attributes.getBoolean(OPEN_CACHE_ANNOTATION_ATTRIBUTE_NAME);
        if (null == openCacheAnnotation) {
            throw new IllegalArgumentException("attribute openCacheAnnotation can not be null!");
        }
        AnnotationMetaHolder.INSTANCE.init(this.attributes);
        String[] imports = selectImports(openCacheAnnotation);
        return imports;
    }

    /**
     * 根据注解配置启用redis缓存
     * @param openCacheAnnotation   true: 开启缓存注解，false: 不开启
     * @return
     */
    protected abstract String[] selectImports(Boolean openCacheAnnotation);
}
