package com.linfengda.sb.support.lettuce.test.multi;

import com.linfengda.sb.support.lettuce.test.helper.ThreadPoolHelper;
import com.linfengda.sb.support.lettuce.test.operation.JedisTestServiceImpl;
import com.linfengda.sb.support.lettuce.test.operation.LettuceTestServiceImpl;
import com.linfengda.sb.support.lettuce.test.operation.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: 客户端测试
 *
 * @author linfengda
 * @create 2019-02-19 10:12
 */
@Slf4j
public class RedisClientTest {
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
    private static ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(THREAD_NUM);




    public static void main(String[] args) {
        //lettuceTest();
        //jedisTest();
        redisQpsTest();
    }

    private static void lettuceTest() {
        try {
            LettuceTestServiceImpl lettuceTestService = new LettuceTestServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong delayCount = new AtomicLong(0);
            newMultiTestTask(lettuceTestService, startCountDown, finishCountDown, delayCount, count);
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
            JedisTestServiceImpl jedisTestService = new JedisTestServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong delayCount = new AtomicLong(0);
            newMultiTestTask(jedisTestService, startCountDown, finishCountDown, delayCount, count);
            log.info("jedis client multi-thread test task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();
            log.info("jedis client multi-thread test task finish, delay percentage: {}%", 100*Double.valueOf(delayCount.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void newMultiTestTask(TestService testService, CountDownLatch startCountDown, CountDownLatch finishCountDown, AtomicLong delayCount, AtomicLong count) {
        for (int i = 0; i < THREAD_NUM; i++) {
            MultiTestTask multiTestTask = new MultiTestTask();
            multiTestTask.setTestService(testService);
            multiTestTask.setStartCountDown(startCountDown);
            multiTestTask.setFinishCountDown(finishCountDown);
            multiTestTask.setCount(count);
            multiTestTask.setDelayCount(delayCount);
            multiTestTask.setTestNum(TEST_NUM);
            multiTestTask.setSlowMillSeconds(SLOW_MILLISECONDS);
            executor.execute(multiTestTask);
            startCountDown.countDown();
        }
    }

    private static void redisQpsTest() {
        try {
            JedisTestServiceImpl jedisTestService = new JedisTestServiceImpl();
            CountDownLatch startCountDown = new CountDownLatch(THREAD_NUM + 1);
            CountDownLatch finishCountDown = new CountDownLatch(THREAD_NUM);
            AtomicLong count = new AtomicLong(0);
            AtomicLong milliseconds1Count = new AtomicLong(0);
            AtomicLong milliseconds10Count = new AtomicLong(0);
            AtomicLong milliseconds20Count = new AtomicLong(0);
            newQpsTestTask(jedisTestService, startCountDown, finishCountDown, count, milliseconds1Count, milliseconds10Count, milliseconds20Count);
            log.info("redis qps test task ready, number of threads: {}", THREAD_NUM);
            startCountDown.countDown();
            finishCountDown.await();

            log.info("redis qps test task finish, {}% <= 1 milliseconds", 100*Double.valueOf(milliseconds1Count.longValue())/Double.valueOf(count.longValue()));
            log.info("redis qps test task finish, {}% <= 10 milliseconds", 100*Double.valueOf(milliseconds10Count.longValue())/Double.valueOf(count.longValue()));
            log.info("redis qps test task finish, {}% <= 20 milliseconds", 100*Double.valueOf(milliseconds20Count.longValue())/Double.valueOf(count.longValue()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void newQpsTestTask(TestService testService, CountDownLatch startCountDown, CountDownLatch finishCountDown, AtomicLong count, AtomicLong milliseconds1Count, AtomicLong milliseconds10Count, AtomicLong milliseconds20Count) {
        for (int i = 0; i < THREAD_NUM; i++) {
            QpsTestTask qpsTestTask = new QpsTestTask();
            qpsTestTask.setTestService(testService);
            qpsTestTask.setStartCountDown(startCountDown);
            qpsTestTask.setFinishCountDown(finishCountDown);
            qpsTestTask.setCount(count);
            qpsTestTask.setMilliseconds1Count(milliseconds1Count);
            qpsTestTask.setMilliseconds10Count(milliseconds10Count);
            qpsTestTask.setMilliseconds20Count(milliseconds20Count);
            qpsTestTask.setTestNum(TEST_NUM);
            qpsTestTask.setSlowMillSeconds1(SLOW_MILLISECONDS1);
            qpsTestTask.setSlowMillSeconds10(SLOW_MILLISECONDS10);
            qpsTestTask.setSlowMillSeconds20(SLOW_MILLISECONDS20);
            executor.execute(qpsTestTask);
            startCountDown.countDown();
        }
    }
}
