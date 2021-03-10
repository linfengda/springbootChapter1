package com.lfd.soa.srv.demo;

import com.lfd.soa.srv.demo.support.apivalidator.annotation.EnableApiValidator;
import com.lfd.soa.srv.demo.support.redis.config.annotation.EnableRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述: 应用启动
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@EnableRedis(enableCacheAnnotation = true, enableBusinessLockAnnotation = true)
@EnableApiValidator()
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE-1)
@SpringBootApplication()
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
