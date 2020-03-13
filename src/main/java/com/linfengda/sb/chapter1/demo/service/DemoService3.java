package com.linfengda.sb.chapter1.demo.service;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.demo.entity.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 描述: @Component注解的类实现BeanFactoryAware接口
 *
 * @author linfengda
 * @create 2020-02-21 22:41
 */
@Slf4j
@Component
public class DemoService3 implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // DefaultListableBeanFactory实现了BeanDefinitionRegistry接口。
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
        // 把需要的实例化的类的属性以property:value的形式放入MutablePropertyValues中，然后放入BeanDefinition中，同时指定要实例化的类类型。
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("name", "旺财");
        BeanDefinition definition = new RootBeanDefinition(Dog.class, null, values);
        // 调用BeanDefinitionRegistry#registerAlias方法
        factory.registerBeanDefinition("dog", definition);
        // 调用getBean获取Dog单例对象
        Dog dog = (Dog) factory.getBean("dog");
        log.info("Dog单例对象={}", JSON.toJSONString(dog));
    }
}
