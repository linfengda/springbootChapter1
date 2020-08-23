package com.linfengda.sb.chapter1;

import com.linfengda.sb.support.apivalidator.annotation.EnableApiValidator;
import com.linfengda.sb.support.fastjson.annotation.EnableFastJsonMessageConvector;
import com.linfengda.sb.support.redis.config.annotation.EnableRedisCacheAnnotation;
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
@EnableFastJsonMessageConvector
@EnableApiValidator(pattern = "xxx")
@EnableRedisCacheAnnotation()
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE-1)
@EnableApplicationStartup
@SpringBootApplication
public class Chapter1Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter1Application.class, args);
	}
}
