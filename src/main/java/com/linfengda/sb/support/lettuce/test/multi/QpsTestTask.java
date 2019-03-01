package com.linfengda.sb.support.lettuce.test.multi;

import com.linfengda.sb.support.lettuce.test.operation.TestService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: qps测试单元
 *
 * @author linfengda
 * @create 2019-02-28 16:31
 */
@Slf4j
@Data
public class QpsTestTask implements Runnable {
    private TestService testService;
    private CountDownLatch startCountDown;
    private CountDownLatch finishCountDown;
    private AtomicLong count;
    private AtomicLong milliseconds1Count;
    private AtomicLong milliseconds10Count;
    private AtomicLong milliseconds20Count;
    private long testNum;
    private long slowMillSeconds1;
    private long slowMillSeconds10;
    private long slowMillSeconds20;


    @Override
    public void run() {
        try {
            startCountDown.await();
            long c = 0;
            while (c < testNum) {
                long t0 = System.currentTimeMillis();
                testService.stringSetOperation();
                long t1 = System.currentTimeMillis()-t0;
                log.info("------------------------------------------------<string command> operation use time={}ms", t1);
                if (t1 < slowMillSeconds20) {
                    milliseconds20Count.incrementAndGet();
                }
                if (t1 < slowMillSeconds10) {
                    milliseconds10Count.incrementAndGet();
                }
                else if (t1 < slowMillSeconds1) {
                    milliseconds1Count.incrementAndGet();
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
}
