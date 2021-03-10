package com.lfd.srv.demo.support.redis;

import com.lfd.common.util.ThreadPoolUtil;
import com.lfd.srv.demo.support.redis.task.DistributeLockTestTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述: redis分布式锁测试
 *
 * @author linfengda
 * @date 2020-07-20 10:04
 */
@Slf4j
@RunWith(JUnit4.class)
public class RedisDistributeLockTest {
    /**
     * 定义并发连接数
     */
    private static final int THREAD_NUM = 100;
    /**
     * 定义线程池
     */
    private static ThreadPoolTaskExecutor executor = ThreadPoolUtil.initThreadPool(THREAD_NUM, THREAD_NUM, 30, 30, "test-thread", new ThreadPoolExecutor.DiscardPolicy());



    /**
     * redis分布式锁测试
     */
    @Test
    public void redisDistributeLockTest() {
        try {
            String lockKey = "myLockKey";
            DistributeLockTestTask lockTestTask1 = new DistributeLockTestTask();
            lockTestTask1.setTaskId(1);
            lockTestTask1.setLockKey(lockKey);
            lockTestTask1.setIsTry(false);
            DistributeLockTestTask lockTestTask2 = new DistributeLockTestTask();
            lockTestTask2.setTaskId(2);
            lockTestTask2.setLockKey(lockKey);
            lockTestTask2.setIsTry(true);

            executor.execute(lockTestTask1);
            Thread.sleep(3000);
            executor.execute(lockTestTask2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
