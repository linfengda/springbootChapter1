package com.lfd.srv.demo.support.queue.config;

import com.lfd.srv.demo.support.queue.annotation.EnableQueueService;
import com.lfd.srv.demo.support.redis.config.AnnotationAttributeHolder;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description
 * @author linfengda
 * @date 2021-01-15 11:45
 */
public class QueueServiceConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableQueueService.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableQueueService is not present on importing class " + importMetadata.getClassName());
        }
        AnnotationAttributeHolder.INSTANCE.init(attributes);
        return new String[]{QueueServiceConfig.class.getName()};
    }
}
