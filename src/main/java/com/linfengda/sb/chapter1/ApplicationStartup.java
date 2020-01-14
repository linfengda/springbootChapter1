package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.common.cache.CacheManager;
import com.linfengda.sb.chapter1.common.context.ApplicationContextHelper;
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
        ApplicationContextHelper.setCtx(event.getApplicationContext());
        // 初始应用程序缓存
        CacheManager.init();
    }
}
