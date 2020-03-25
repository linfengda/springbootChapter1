package com.linfengda.sb.support.middleware.redis.cache;

import com.linfengda.sb.support.middleware.redis.cache.annotation.EnableBizQueryCache;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 注册业务查询缓存配置
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class BizQueryCacheImportBeanDefinitionSelector extends AdviceModeImportSelector<EnableBizQueryCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {BizQueryCacheConfiguration.class.getName()};
    }
}
