package com.lfd.srv.demo.support.queue.config;

import com.lfd.srv.demo.support.queue.annotation.QueueService;
import com.lfd.srv.demo.support.queue.proxy.QueueServiceProxyFactoryBean;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * @description 扫描并注册bean到spring容器
 * @author linfengda
 * @date 2021-01-15 11:07
 */
public class QueueServiceScanner extends ClassPathBeanDefinitionScanner {

    public QueueServiceScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public void scanAndRegisterBean(String... basePackages){
        doScan(basePackages);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件
        addIncludeFilter(new AnnotationTypeFilter(QueueService.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        registrarBean(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @SneakyThrows
    private void registrarBean(Set<BeanDefinitionHolder> beanDefinitionHolders){
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            Class<?> clazz = Class.forName(beanClassName);
            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            definition.setBeanClass(QueueServiceProxyFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            super.getRegistry().registerBeanDefinition(convertClassSimpleName(clazz.getSimpleName()), definition);
        }

        // 1.解析并缓存注解元数据
        // 2.实例化SimpleRabbitListenerContainerFactory和队列
    }

    private String convertClassSimpleName(String simpleName){
        return Character.toLowerCase(simpleName.charAt(0))+simpleName.substring(1);
    }
}
