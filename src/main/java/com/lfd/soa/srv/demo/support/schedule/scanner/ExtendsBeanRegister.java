package com.lfd.soa.srv.demo.support.schedule.scanner;

import com.lfd.soa.srv.demo.support.schedule.config.ScheduleAttributeHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 定时任务注解扫描并注册
 *
 * @author linfengda
 * @date 2021-02-03 16:30
 */
@Slf4j
public class ExtendsBeanRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 初始化扫描定时任务类
        TaskControllerScanner taskControllerScanner = new TaskControllerScanner(beanDefinitionRegistry, false);
        taskControllerScanner.doScanner(ScheduleAttributeHolder.INSTANCE.getAttributes().getString("basePackage"));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
