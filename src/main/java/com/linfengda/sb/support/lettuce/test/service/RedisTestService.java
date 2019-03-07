package com.linfengda.sb.support.lettuce.test.service;

import com.linfengda.sb.support.lettuce.test.helper.JedisTemplateHelper;
import com.linfengda.sb.support.lettuce.test.helper.ThreadPoolHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: redis临时测试任务
 *
 * @author linfengda
 * @create 2019-03-07 10:16
 */
@Slf4j
public class RedisTestService {
    private static ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(2);
    private static RedisTemplate<String, Object> redisTemplate = JedisTemplateHelper.getTemplate();
    private static final String TEMP_KEY = "tempKey";

    public static void main(String[] args) {
        testCallerRuns();
    }

    public static void testCallerRuns() {
        redisTemplate.delete(TEMP_KEY);
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForList().rightPush(TEMP_KEY, i);
        }
        for (int i = 0; i < 3; i++) {
            if (i < 2) {
                RedisTestTask redisTestTask = new RedisTestTask();
                redisTestTask.setI(i);
                executor.execute(redisTestTask);
            }else {
                while (redisTemplate.opsForList().size(TEMP_KEY) != 0) {
                    log.info("left pop list: {}, thread: {}", redisTemplate.opsForList().leftPop(TEMP_KEY), Thread.currentThread().getName());
                }
            }
        }
    }

    @Data
    static class RedisTestTask implements Runnable {
        private int i = 0;
        @Override
        public void run() {
            try {
                while (redisTemplate.opsForList().size(TEMP_KEY) != 0) {
                    log.info("left pop list: {}, thread: {}", redisTemplate.opsForList().leftPop(TEMP_KEY), Thread.currentThread().getName());
                    Thread.sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
