package com.linfengda.sb.chapter1.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author linfengda
 * @date 2020-09-22 14:36
 */
@Configuration
@EnableFeignClients(basePackages = "com.linfengda.sb.chapter1")
public class FeignConfig {

}
