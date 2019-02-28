package com.linfengda.sb.support.lettuce.test.multi;

import com.linfengda.sb.support.lettuce.test.helper.ThreadPoolHelper;
import com.linfengda.sb.support.lettuce.test.operation.JedisTestService;
import com.linfengda.sb.support.lettuce.test.operation.LettuceTestService;
import com.linfengda.sb.support.lettuce.test.operation.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: lettuce客户端并发测试
 *
 * @author linfengda
 * @create 2019-02-19 10:12
 */
@Slf4j
public class ClientMultiThreadTest implements BenchmarkConstant {
    private static ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(THREAD_NUM);

    public static void main(String[] args) {
        //lettuceTest();
        jedisTest();
    }

    private static void lettuceTest() {
        try {
            LettuceTestService lettuceTestService = new LettuceTestService();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong delayCount = new AtomicLong(0);
            AtomicLong count = new AtomicLong(0);
            newTestTask(lettuceTestService, startCountDown, finishCountDown, delayCount, count);
            log.info("lettuce client multi-thread test task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();
            log.info("lettuce client multi-thread test task finish, delay percentage: {}%", 100*Double.valueOf(delayCount.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void jedisTest() {
        try {
            JedisTestService jedisTestService = new JedisTestService();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong delayCount = new AtomicLong(0);
            AtomicLong count = new AtomicLong(0);
            newTestTask(jedisTestService, startCountDown, finishCountDown, delayCount, count);
            log.info("jedis client multi-thread test task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();
            log.info("jedis client multi-thread test task finish, delay percentage: {}%", 100*Double.valueOf(delayCount.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void newTestTask(TestService testService, CountDownLatch startCountDown, CountDownLatch finishCountDown, AtomicLong delayCount, AtomicLong count) {
        for (int i = 0; i < THREAD_NUM; i++) {
            MultiThreadTestTask multiThreadTestTask = new MultiThreadTestTask();
            multiThreadTestTask.setTestService(testService);
            multiThreadTestTask.setStartCountDown(startCountDown);
            multiThreadTestTask.setFinishCountDown(finishCountDown);
            multiThreadTestTask.setDelayCount(delayCount);
            multiThreadTestTask.setCount(count);
            executor.execute(multiThreadTestTask);
            startCountDown.countDown();
        }
    }
}
