package com.lfd.srv.demo.support.redis;

import com.lfd.common.util.ThreadPoolUtil;
import com.lfd.srv.demo.support.redis.service.JedisRedisOperationServiceImpl;
import com.lfd.srv.demo.support.redis.service.LettuceRedisOperationServiceImpl;
import com.lfd.srv.demo.support.redis.service.RedisOperationService;
import com.lfd.srv.demo.support.redis.task.OperationTask;
import com.lfd.srv.demo.support.redis.task.QpsTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: 客户端并发性能测试
 *
 * @author linfengda
 * @create 2019-02-19 10:12
 */
@Slf4j
@RunWith(JUnit4.class)
public class RedisClientPerformanceTest {
    /**
     * 定义并发连接数
     */
    private static final int THREAD_NUM = 100;
    /**
     * 定义请求数
     */
    private static final long TEST_NUM = 10000;
    /**
     * 定义慢查询阈值
     */
    private static final long SLOW_MILLISECONDS = 30;

    /**
     * 定义慢查询1ms
     */
    private static final long SLOW_MILLISECONDS1 = 1;
    /**
     * 定义慢查询10ms
     */
    private static final long SLOW_MILLISECONDS10 = 10;
    /**
     * 定义慢查询20ms
     */
    private static final long SLOW_MILLISECONDS20 = 20;
    /**
     * 定义线程池
     */
    private static ThreadPoolTaskExecutor executor = ThreadPoolUtil.initThreadPool(THREAD_NUM, THREAD_NUM, 30, 30, "test-thread", new ThreadPoolExecutor.DiscardPolicy());



    /**
     * 测试lettuce客户端并发查询延迟率
     */
    @Test
    public void lettuceDelayOperationTest() {
        try {
            LettuceRedisOperationServiceImpl lettuceTestService = new LettuceRedisOperationServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong delayCount = new AtomicLong(0);
            createOperationTask(lettuceTestService, startCountDown, finishCountDown, delayCount, count);
            log.info("redis client multi-thread task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();
            log.info("redis client multi-thread task finish, delay percentage: {}%", 100*Double.valueOf(delayCount.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试jedis客户端并发查询延迟率
     */
    @Test
    public void jedisDelayOperationTest() {
        try {
            JedisRedisOperationServiceImpl jedisTestService = new JedisRedisOperationServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong delayCount = new AtomicLong(0);
            createOperationTask(jedisTestService, startCountDown, finishCountDown, delayCount, count);
            log.info("jedis client multi-thread task task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();
            log.info("jedis client multi-thread task task finish, delay percentage: {}%", 100*Double.valueOf(delayCount.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createOperationTask(RedisOperationService redisOperationService, CountDownLatch startCountDown, CountDownLatch finishCountDown, AtomicLong delayCount, AtomicLong count) {
        for (int i = 0; i < THREAD_NUM; i++) {
            OperationTask operationTask = new OperationTask();
            operationTask.setRedisOperationService(redisOperationService);
            operationTask.setStartCountDown(startCountDown);
            operationTask.setFinishCountDown(finishCountDown);
            operationTask.setCount(count);
            operationTask.setDelayCount(delayCount);
            operationTask.setTestNum(TEST_NUM);
            operationTask.setSlowMillSeconds(SLOW_MILLISECONDS);
            executor.execute(operationTask);
            startCountDown.countDown();
        }
    }

    /**
     * 测试redis客户端查询延迟率与并发数关系
     */
    @Test
    public void redisQpsTest() {
        try {
            JedisRedisOperationServiceImpl jedisTestService = new JedisRedisOperationServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong milliseconds1Count = new AtomicLong(0);
            AtomicLong milliseconds10Count = new AtomicLong(0);
            AtomicLong milliseconds20Count = new AtomicLong(0);
            createQpsTask(jedisTestService, startCountDown, finishCountDown, count, milliseconds1Count, milliseconds10Count, milliseconds20Count);
            log.info("redis qps task task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();

            log.info("redis qps task task finish, {}% <= 1 milliseconds", 100*Double.valueOf(milliseconds1Count.longValue())/Double.valueOf(count.longValue()));
            log.info("redis qps task task finish, {}% <= 10 milliseconds", 100*Double.valueOf(milliseconds10Count.longValue())/Double.valueOf(count.longValue()));
            log.info("redis qps task task finish, {}% <= 20 milliseconds", 100*Double.valueOf(milliseconds20Count.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createQpsTask(RedisOperationService redisOperationService, CountDownLatch startCountDown, CountDownLatch finishCountDown, AtomicLong count, AtomicLong milliseconds1Count, AtomicLong milliseconds10Count, AtomicLong milliseconds20Count) {
        for (int i = 0; i < THREAD_NUM; i++) {
            QpsTask qpsTask = new QpsTask();
            qpsTask.setRedisOperationService(redisOperationService);
            qpsTask.setStartCountDown(startCountDown);
            qpsTask.setFinishCountDown(finishCountDown);
            qpsTask.setCount(count);
            qpsTask.setMilliseconds1Count(milliseconds1Count);
            qpsTask.setMilliseconds10Count(milliseconds10Count);
            qpsTask.setMilliseconds20Count(milliseconds20Count);
            qpsTask.setTestNum(TEST_NUM);
            qpsTask.setSlowMillSeconds1(SLOW_MILLISECONDS1);
            qpsTask.setSlowMillSeconds10(SLOW_MILLISECONDS10);
            qpsTask.setSlowMillSeconds20(SLOW_MILLISECONDS20);
            executor.execute(qpsTask);
            startCountDown.countDown();
        }
    }
}
