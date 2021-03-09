package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.cache.type.SystemCacheManager;
import com.linfengda.sb.support.orm.auto.UserAware;
import com.linfengda.sb.support.orm.auto.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * 描述: 应用启动监听
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@Configuration
@Slf4j
public class ApplicationStartup {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("springboot应用程序初始化中......");
        SystemCacheManager.init();
        ApplicationContext applicationContext = event.getApplicationContext();
        UserHolder.init(applicationContext.getBean(UserAware.class));
        log.info("springboot应用程序初始化完成，当前版本{}", Constant.VERSION);
    }
}
