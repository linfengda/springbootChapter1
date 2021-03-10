package com.lfd.soa.srv.demo.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author linfengda
 * @date 2020-09-22 14:36
 */
@Configuration
@EnableFeignClients(basePackages = "com.lfd.srv.demo.chapter1")
public class FeignConfig {

}
