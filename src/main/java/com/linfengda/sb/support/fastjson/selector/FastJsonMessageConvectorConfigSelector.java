package com.linfengda.sb.support.fastjson.selector;

import com.linfengda.sb.support.fastjson.config.FastJsonMessageConvectorConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 引入配置类
 *
 * @author: linfengda
 * @date: 2020-07-26 22:36
 */
public class FastJsonMessageConvectorConfigSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {FastJsonMessageConvectorConfig.class.getName()};
    }
}
