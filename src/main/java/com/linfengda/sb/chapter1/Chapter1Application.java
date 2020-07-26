package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.redis.cache.manager.AopOrderManager;
import com.linfengda.sb.support.redis.config.annotation.EnableRedisCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 应用启动
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@EnableRedisCache
@EnableTransactionManagement(order = AopOrderManager.SPRING_TRANSACTION)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
