package com.linfengda.sb.support.lettuce.test.multi;

import com.linfengda.sb.support.lettuce.config.ClientType;
import com.linfengda.sb.support.lettuce.test.operation.JedisTestService;
import com.linfengda.sb.support.lettuce.test.operation.LettuceTestService;
import com.linfengda.sb.support.lettuce.test.operation.TestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: 定义客户端并发性能测试单元
 *
 * @author linfengda
 * @create 2019-02-19 11:35
 */
@Slf4j
@Data
public class MultiThreadTestTask implements BenchmarkConstant, Runnable {
    private TestService testService;
    private CountDownLatch startCountDown;
    private CountDownLatch finishCountDown;
    private AtomicLong delayCount;
    private AtomicLong count;

    @Override
    public void run() {
        try {
            startCountDown.await();
            log.info("client multi-thread test task start, threadId: {}", Thread.currentThread().getId());
            long t0 = System.currentTimeMillis();
            while (System.currentTimeMillis() < t0 + TEST_SECONDS) {
                boolean isDelay = isDelay();
                if (isDelay){
                    delayCount.incrementAndGet();
                }
                count.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finishCountDown.countDown();
        }
    }

    private boolean isDelay() throws Exception {
        long t0 = System.currentTimeMillis();
        testService.StringSetOperation();
        long t1 = System.currentTimeMillis()-t0;
        log.info("------------------------------------------------<string command> operation use time={}ms", t1);
        if (t1 > SLOW_MILLISECONDS) {
            return true;
        }
        return false;
    }
}
