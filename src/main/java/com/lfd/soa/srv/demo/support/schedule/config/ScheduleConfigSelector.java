package com.lfd.soa.srv.demo.support.schedule.config;

import com.lfd.soa.srv.demo.support.schedule.annotation.EnableSchedule;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author linfengda
 * @date 2021-02-03 16:15
 */
public class ScheduleConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableSchedule.class.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException("@EnableSchedule is not present on importing class " + importMetadata.getClassName());
        }
        ScheduleAttributeHolder.INSTANCE.init(attributes);
        return new String[]{ScheduleConfig.class.getName()};
    }
}
