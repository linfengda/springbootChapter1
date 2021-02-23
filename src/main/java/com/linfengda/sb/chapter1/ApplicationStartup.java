package com.linfengda.sb.chapter1;

import com.linfengda.sb.chapter1.cache.SystemCacheManager;
import lombok.extern.slf4j.Slf4j;
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
        // 初始应用程序缓存
        SystemCacheManager.init();

       /* GenericRedisTemplate genericRedisTemplate = event.getApplicationContext().getBean(GenericRedisTemplate.class);
        genericRedisTemplate.setObject("aaa:1", "1");
        genericRedisTemplate.setObject("aaa:2", "2");
        genericRedisTemplate.setObject("aaa:3", "3");
        genericRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match("aaa*").count(100).build());
            while(cursor.hasNext()) {
                String key = new String(cursor.next());
                genericRedisTemplate.delete(key);
            }
            return true;
        });*/
        log.info("springboot应用程序初始化完成，当前版本{}", Constant.VERSION);
    }
}
