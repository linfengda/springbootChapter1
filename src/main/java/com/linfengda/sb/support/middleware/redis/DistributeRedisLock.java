package com.linfengda.sb.support.middleware.redis;

import com.linfengda.sb.common.util.ServerRunTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DistributeRedisLock {
    /**
     * Redis分布式锁默认超时时间
     */
    private final int DEFAULT_LOCK_EXPIRE_TIME = 60;

    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    protected boolean tryLock(String[] keys) throws Exception {
        Map map = new HashMap();
        for (String key : keys) {
            map.put(key, getCurrentThreadId());
        }
        Boolean result = simpleRedisTemplate.opsForValue().multiSetIfAbsent(map);
        for (String key : keys) {
            simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        if (result) {
            log.info("Thread[id=" +getCurrentThreadId()+ "]" + "lock success");
        }
        return result;
    }

    protected boolean tryLock(String key) throws Exception {
        Boolean result = simpleRedisTemplate.opsForValue().setIfAbsent(key, getCurrentThreadId());
        simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
        if (result) {
            log.info("Thread[id=" +getCurrentThreadId()+ "]" + "lock success");
        }
        return result;
    }

    protected boolean unLock(String[] keys) throws Exception {
        for (String key : keys) {
            unLock(key);
        }
        return true;
    }

    protected boolean unLock(String key) throws Exception {
        String threadId = simpleRedisTemplate.getObject(key, String.class);
        String currentThreadId = getCurrentThreadId();
        if (null == threadId) {return true;}
        if (threadId.equals(currentThreadId)) {
            simpleRedisTemplate.delete(key);
            log.info("Thread[id=" +currentThreadId+ "]" + "unlock success");
            return true;
        }
        return false;
    }

    private String getCurrentThreadId(){
        return String.valueOf(Thread.currentThread().getId())+ ServerRunTimeUtil.getMac();
    }
}
