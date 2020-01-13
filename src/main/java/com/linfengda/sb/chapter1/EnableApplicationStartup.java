package com.linfengda.sb.chapter1;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述: 是否进行应用程序初始化
 *
 * @author linfengda
 * @create 2020-01-13 16:25
 */
@Import(ApplicationStartup.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableApplicationStartup {
}
