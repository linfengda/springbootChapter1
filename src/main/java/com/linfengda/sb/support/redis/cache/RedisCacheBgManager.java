package com.linfengda.sb.support.redis.cache;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.cache.entity.bo.LruExpireResultBO;
import com.linfengda.sb.support.redis.util.CacheUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;

/**
 * 描述: 缓存后台管理
 *
 * @author: linfengda
 * @date: 2020-07-21 14:57
 */
@Slf4j
public enum RedisCacheBgManager {
    /**
     * 单例
     */
    INSTANCE;
    @Setter
    private GenericRedisTemplate genericRedisTemplate;

    public void start(final long internalTime) {
        Thread bgThread = new Thread(() -> {
            while(true) {
                try {
                    // 使用scan渐进删除
                    LruExpireResultBO lruExpireResultBO = genericRedisTemplate.execute(new RedisCallback<LruExpireResultBO>() {
                        LruExpireResultBO lruExpireResultBO = new LruExpireResultBO();
                        long startTime = System.currentTimeMillis();

                        @Override
                        public LruExpireResultBO doInRedis(RedisConnection connection) throws DataAccessException {
                            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(Constant.LRU_RECORD_PREFIX + Constant.ASTERISK).count(Constant.DEFAULT_BG_REMOVE_LRU_BATCH_NUM).build());
                            while(cursor.hasNext()) {
                                String lruKey = new String(cursor.next());
                                genericRedisTemplate.opsForZSet().removeRangeByScore(lruKey, 0, CacheUtil.getKeyLruScore());
                                lruExpireResultBO.addLruKeyNum();
                                log.info("批量清除LRU缓存记录，position={}，lruKey={}", cursor.getPosition(), lruKey);
                            }

                            lruExpireResultBO.setCostTime(System.currentTimeMillis()-startTime);
                            return lruExpireResultBO;
                        }
                    });
                    log.info(lruExpireResultBO.getExpireMsg());
                }catch (Exception e) {
                    log.error("清除LRU缓存失败！", e);
                }finally {
                    try {
                        Thread.sleep(internalTime);
                    }catch (Exception e) {
                        log.error("清除LRU缓存休眠失败！");
                    }
                }
            }
        });
        bgThread.setDaemon(true);
        bgThread.start();
    }
}
