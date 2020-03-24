package com.linfengda.sb.support.middleware.redis.cache;

import com.linfengda.sb.chapter1.common.config.MyCacheConfiguration;
import com.linfengda.sb.support.middleware.redis.cache.annotation.EnableMyCache;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * 描述: 在容器初始化bean后加入缓存注解增强器
 *
 * @author linfengda
 * @create 2020-03-24 18:03
 */
public class MyCacheImportBeanDefinitionRegistrar extends AdviceModeImportSelector<EnableMyCache> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[] {MyCacheConfiguration.class.getName()};
    }
}
