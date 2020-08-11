package com.linfengda.sb.support.apivalidator.config;

import com.linfengda.sb.support.apivalidator.config.resolver.MyControllerArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 描述: Spring MVC配置
 *
 * @author linfengda
 * @create 2019-12-18 09:53
 */
public class ApiValidatorWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MyControllerArgumentResolver());
    }
}
