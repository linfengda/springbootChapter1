package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.common.cache.manager.CacheManager;
import com.linfengda.sb.chapter1.common.context.ApplicationContextHelper;
import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
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
        ApplicationContext ctx = event.getApplicationContext();
        ApplicationContextHelper.setCtx(ctx);


        JacksonRedisTemplate jacksonRedisTemplate = SpringUtil.getBean(JacksonRedisTemplate.class);
        jacksonRedisTemplate.setObject("key", "value");
        // 初始应用程序缓存
        CacheManager.init();
    }
}
