package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.middleware.redis.cache.annotation.EnableMyCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableMyCache
@EnableAspectJAutoProxy(exposeProxy = false, proxyTargetClass = false)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = false)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
