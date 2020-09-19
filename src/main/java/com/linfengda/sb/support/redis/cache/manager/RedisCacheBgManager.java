package com.linfengda.sb.support.redis.cache.manager;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import com.linfengda.sb.support.redis.cache.entity.bo.LruExpireResultBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;

/**
 * 描述: redis会自动清理过期的lru类型缓存，但并不会清理lru keys，因此需要本地后台线程清理。
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
    private GenericRedisTemplate genericRedisTemplate;
    private RedisDistributedLock redisDistributedLock;

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
                            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(Constant.DEFAULT_LRU_RECORD_PREFIX + Constant.ASTERISK).count(Constant.DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM).build());
                            while(cursor.hasNext()) {
                                String lruKey = new String(cursor.next());
                                try {
                                    // 使用锁实现多台实例分布式淘汰
                                    if (redisDistributedLock.tryLock(Constant.DEFAULT_BG_LRU_LOCK_PREFIX + Constant.COLON + lruKey)) {
                                        // 淘汰过期key的lru缓存标识
                                        genericRedisTemplate.opsForZSet().removeRangeByScore(lruKey, 0, (double) System.currentTimeMillis());
                                        lruExpireResultBO.addLruKeyNum();
                                        log.info("批量清理LRU缓存记录，position={}，lruKey={}", cursor.getPosition(), lruKey);
                                    }
                                }finally {
                                    redisDistributedLock.unLock(Constant.DEFAULT_BG_LRU_LOCK_PREFIX + Constant.COLON + lruKey);
                                }
                            }
                            lruExpireResultBO.setCostTime(System.currentTimeMillis()-startTime);
                            return lruExpireResultBO;
                        }
                    });
                    log.info(lruExpireResultBO.getExpireMsg());
                }catch (Exception e) {
                    log.error("清理LRU缓存失败！", e);
                }finally {
                    try {
                        Thread.sleep(internalTime);
                    }catch (Exception e) {
                        log.error("清理LRU缓存休眠失败！");
                    }
                }
            }
        });
        bgThread.setDaemon(true);
        bgThread.start();
    }

    public void init(GenericRedisTemplate genericRedisTemplate, RedisDistributedLock redisDistributedLock) {
        this.genericRedisTemplate = genericRedisTemplate;
        this.redisDistributedLock = redisDistributedLock;
    }
}
