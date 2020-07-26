package com.linfengda.sb.support.redis.config;

import com.linfengda.sb.support.redis.config.annotation.EnableRedisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 描述: 获取{@link EnableRedisCache}注解信息
 *
 * @author: linfengda
 * @date: 2020-07-02 15:51
 */
@Configuration
public class AbstractCacheConfig implements ImportAware {
    protected AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.attributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableRedisCache.class.getName(), false));
        if (this.attributes == null) {
            throw new IllegalArgumentException(
                    "@EnableCache is not present on importing class " + importMetadata.getClassName());
        }
    }
}
