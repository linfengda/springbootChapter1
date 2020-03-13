package com.linfengda.sb.chapter1.common.config;

import com.linfengda.sb.chapter1.common.api.parameter.MyControllerArgumentResolver;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 描述: Spring MVC配置
 *
 * @author linfengda
 * @create 2019-12-18 09:53
 */
@SpringBootConfiguration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MyControllerArgumentResolver());
    }
}
