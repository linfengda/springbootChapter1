package com.linfengda.sb.support.redis.task;

import com.linfengda.sb.support.util.ServerRunTimeUtil;
import com.linfengda.sb.support.redis.helper.JedisTemplateHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 描述: 分布式锁测试单元
 *
 * @author linfengda
 * @create 2019-03-01 20:38
 */
@Slf4j
@Data
public class DistributeLockTestTask implements Runnable {
    /**
     * Redis分布式锁默认超时时间
     */
    private final int DEFAULT_LOCK_EXPIRE_TIME = 60;
    private static RedisTemplate<String, Object> redisTemplate = JedisTemplateHelper.getTemplate();
    private Integer taskId;
    private String lockKey;
    private Boolean isTry;

    @Override
    public void run() {
        try {
            if (isTry) {
                while (true) {
                    tryLock();
                }
            } else {
                redisTemplate.delete(lockKey);
                tryLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryLock() {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, getCurrentThreadId());
        if (result) {
            redisTemplate.expire(lockKey, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            log.info("taskId={}, time={} lock success", taskId, System.currentTimeMillis());
        }
    }

    private String getCurrentThreadId(){
        return String.valueOf(Thread.currentThread().getId())+ ServerRunTimeUtil.getMac();
    }
}
