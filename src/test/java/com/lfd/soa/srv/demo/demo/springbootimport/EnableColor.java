package com.lfd.soa.srv.demo.demo.springbootimport;

import com.lfd.soa.srv.demo.demo.springbootimport.color.Red;
import com.lfd.soa.srv.demo.demo.springbootimport.target.ColorImportBeanDefinitionRegistrar;
import com.lfd.soa.srv.demo.demo.springbootimport.target.ColorImportSelector;
import com.lfd.soa.srv.demo.demo.springbootimport.target.ColorRegistrarConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述: @Import可以传入四种类型：普通类、配置类、ImportSelector的实现类，ImportBeanDefinitionRegistrar的实现类。
 *
 * @author linfengda
 * @create 2019-12-30 15:01
 */
@Import({Red.class, ColorRegistrarConfiguration.class, ColorImportSelector.class, ColorImportBeanDefinitionRegistrar.class})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableColor {
}
