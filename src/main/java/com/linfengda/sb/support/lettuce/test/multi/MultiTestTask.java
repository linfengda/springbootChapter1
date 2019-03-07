package com.linfengda.sb.support.lettuce.test.multi;

import com.linfengda.sb.support.lettuce.test.service.TestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: 客户端并发性能测试单元
 *
 * @author linfengda
 * @create 2019-02-19 11:35
 */
@Slf4j
@Data
public class MultiTestTask implements Runnable {
    private TestService testService;
    private CountDownLatch startCountDown;
    private CountDownLatch finishCountDown;
    private AtomicLong count;
    private AtomicLong delayCount;
    private long testNum;
    private long slowMillSeconds;

    @Override
    public void run() {
        try {
            startCountDown.await();
            long c = 0;
            while (c < testNum) {
                boolean isDelay = test();
                if (isDelay){
                    delayCount.incrementAndGet();
                }
                count.incrementAndGet();
                c++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finishCountDown.countDown();
        }
    }

    private boolean test() throws Exception {
        long t0 = System.currentTimeMillis();
        testService.stringSetGetOperation();
        long t1 = System.currentTimeMillis()-t0;
        log.info("------------------------------------------------<string command> service use time={}ms", t1);
        if (t1 > slowMillSeconds) {
            return true;
        }
        return false;
    }
}
