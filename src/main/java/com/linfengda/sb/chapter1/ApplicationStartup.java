package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.common.cache.manager.CacheManager;
import com.linfengda.sb.support.redis.template.SimpleRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * 描述: 应用启动监听
 *
 * @author linfengda
 * @create 2020-01-09 09:18
 */
@Slf4j
public class ApplicationStartup {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("springboot应用程序初始化中......");
        // 初始应用程序缓存
        CacheManager.init();

        //SimpleRedisTemplate simpleRedisTemplate = event.getApplicationContext().getBean(SimpleRedisTemplate.class);
        log.info("springboot应用程序初始化完成，当前版本{}", Constant.VERSION);
    }
}
