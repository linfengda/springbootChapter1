package com.linfengda.sb.support.queue.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2019/9/30 15:08
 */
public class QueueServiceBeanRegister implements BeanDefinitionRegistryPostProcessor {
    private static final String BASE_SCAN_PACKAGE = "baseScanPackage";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        QueueServiceScanner queueServiceScanner = new QueueServiceScanner(beanDefinitionRegistry,false);
        AnnotationAttributes attributes = AnnotationAttributeHolder.INSTANCE.getAttributes();
        String baseScanPage = attributes.getString(BASE_SCAN_PACKAGE);
        queueServiceScanner.scanAndRegisterBean(baseScanPage.split(","));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
