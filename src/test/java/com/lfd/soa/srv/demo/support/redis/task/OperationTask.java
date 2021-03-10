package com.lfd.soa.srv.demo.support.redis.task;

import com.lfd.soa.srv.demo.support.redis.service.RedisOperationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述: 重复执行某个redis操作，并记录延迟次数
 *
 * @author linfengda
 * @create 2019-02-19 11:35
 */
@Slf4j
@Data
public class OperationTask implements Runnable {
    private RedisOperationService redisOperationService;
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
                long t0 = System.currentTimeMillis();
                redisOperationService.stringSetGetOperation();
                long t1 = System.currentTimeMillis()-t0;
                log.info("------------------------------------------------<string command> service use time={}ms", t1);
                if (t1 > slowMillSeconds) {
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
}
