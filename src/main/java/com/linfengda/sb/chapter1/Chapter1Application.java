package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.cache.config.EnableQueryCache;
import com.linfengda.sb.support.cache.config.EnableUpdateCache;
import com.linfengda.sb.support.cache.manager.CacheAopOrderManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableQueryCache(order = CacheAopOrderManager.QUERY_CACHE)
@EnableUpdateCache(order = CacheAopOrderManager.UPDATE_CACHE)
@EnableTransactionManagement(order = CacheAopOrderManager.SPRING_TRANSACTION)
@EnableAspectJAutoProxy(exposeProxy = false, proxyTargetClass = false)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
