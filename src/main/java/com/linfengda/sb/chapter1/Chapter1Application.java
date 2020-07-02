package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.cache.config.EnableCache;
import com.linfengda.sb.support.cache.manager.CacheAopOrderManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 应用启动
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@EnableCache()
@EnableTransactionManagement(order = CacheAopOrderManager.SPRING_TRANSACTION)
@EnableAspectJAutoProxy(exposeProxy = false, proxyTargetClass = false)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
