package com.linfengda.sb.support.cache.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 注册查询缓存配置
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class QueryCacheImportBeanDefinitionSelector extends AdviceModeImportSelector<EnableQueryCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {QueryCacheConfiguration.class.getName()};
    }
}
