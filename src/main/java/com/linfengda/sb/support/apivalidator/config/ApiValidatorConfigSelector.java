package com.linfengda.sb.support.apivalidator.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 引入切面拦截api参数
 *
 * @author: linfengda
 * @date: 2020-07-26 22:36
 */
public class ApiValidatorConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {ApiValidatorAdvisorConfig.class.getName(), ApiValidatorWebMvcConfig.class.getName()};
    }
}
