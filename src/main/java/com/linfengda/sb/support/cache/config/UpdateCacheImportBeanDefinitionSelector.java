package com.linfengda.sb.support.cache.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 注册更新缓存配置
 *
 * @author linfengda
 * @create 2020-03-26 11:24
 */
public class UpdateCacheImportBeanDefinitionSelector extends AdviceModeImportSelector<EnableUpdateCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {UpdateCacheConfiguration.class.getName()};
    }
}
