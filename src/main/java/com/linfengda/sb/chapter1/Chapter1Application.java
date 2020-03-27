package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.cache.annotation.EnableQueryCache;
import com.linfengda.sb.support.cache.annotation.EnableUpdateCache;
import com.linfengda.sb.support.cache.manager.AopOrderManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableQueryCache(order = AopOrderManager.QUERY_CACHE)
@EnableUpdateCache(order = AopOrderManager.UPDATE_CACHE)
@EnableTransactionManagement(order = AopOrderManager.SPRING_TRANSACTION)
@EnableAspectJAutoProxy(exposeProxy = false, proxyTargetClass = false)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
