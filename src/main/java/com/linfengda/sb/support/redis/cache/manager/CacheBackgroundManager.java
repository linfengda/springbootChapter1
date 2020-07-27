package com.linfengda.sb.support.redis.cache.manager;

import com.linfengda.sb.support.redis.config.Constant;
import com.linfengda.sb.support.redis.config.RedisSupportHolder;
import com.linfengda.sb.support.redis.cache.entity.bo.LruExpireResultBO;
import com.linfengda.sb.support.redis.template.SimpleRedisTemplate;
import com.linfengda.sb.support.redis.cache.util.CacheUtil;
import com.linfengda.sb.support.redis.cache.util.ThreadPoolHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;

/**
 * 描述: 缓存后台管理
 *
 * @author: linfengda
 * @date: 2020-07-21 14:57
 */
@Slf4j
@SpringBootConfiguration
public class CacheBackgroundManager {
    /**
     * 缓存后台管理线程池
     */
    private static final ThreadPoolTaskExecutor cacheBgThreadPool = ThreadPoolHelper.initThreadPool(1, 10, "cache-bg-thread");

    @PostConstruct
    public void init() {
        cacheBgThreadPool.execute(() -> {
            while(true) {
                try {
                    Thread.sleep(Constant.DEFAULT_LRU_CACHE_CLEAR_TASK);

                    // 使用scan渐进删除
                    SimpleRedisTemplate simpleRedisTemplate = RedisSupportHolder.getSimpleRedisTemplate();
                    LruExpireResultBO lruExpireResultBO = simpleRedisTemplate.execute(new RedisCallback<LruExpireResultBO>() {
                        LruExpireResultBO lruExpireResultBO = new LruExpireResultBO();
                        long startTime = System.currentTimeMillis();

                        @Override
                        public LruExpireResultBO doInRedis(RedisConnection connection) throws DataAccessException {
                            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(Constant.LRU_RECORD_PREFIX + Constant.ASTERISK).count(Constant.DEFAULT_BG_LRU_REMOVE_BATCH_NUM).build());
                            while(cursor.hasNext()) {
                                String lruKey = new String(cursor.next());
                                simpleRedisTemplate.opsForZSet().removeRangeByScore(lruKey, 0, CacheUtil.getKeyLruScore());
                                lruExpireResultBO.addLruKeyNum();
                                log.info("批量清除LRU缓存记录，position={}，lruKey={}", cursor.getPosition(), lruKey);
                            }

                            lruExpireResultBO.setCostTime(System.currentTimeMillis()-startTime);
                            return lruExpireResultBO;
                        }
                    });
                    log.info(lruExpireResultBO.getExpireMsg());
                }catch (Exception e) {
                    log.error("清除LRU缓存失败！");
                }
            }
        });
    }
}