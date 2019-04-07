package com.linfengda.sb.chapter1.hystrix.task;

import com.linfengda.sb.chapter1.hystrix.entity.RequestVo;
import com.linfengda.sb.chapter1.hystrix.service.HystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 描述: 熔断调用
 *
 * @author linfengda
 * @create 2019-04-04 17:00
 */
@Slf4j
@Component
@EnableScheduling
public class HystrixTestJob {
    @Resource
    private HystrixService hystrixService;

    //@Scheduled(cron = "*/10 * * * * ?")
    /*public void test1() throws Exception {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 11; i++) {
            hystrixService.hysOverThread(i);
        }
        log.info("定时任务执行成功！耗时：{}ms", (System.currentTimeMillis() - t1));
    }*/

    //@Scheduled(cron = "*/10 * * * * ?")
    /*public void test2() throws Exception {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            hystrixService.hysOverThread(i);
            hystrixService.hysWithThreadPoolKey(i);
        }
        log.info("定时任务执行成功！耗时：{}ms", (System.currentTimeMillis() - t1));
    }*/

    //@Scheduled(cron = "*/10 * * * * ?")
    /*public void test3() throws Exception {
        long t1 = System.currentTimeMillis();
        hystrixService.hysTimeOut();
        log.info("定时任务执行成功！耗时：{}ms", (System.currentTimeMillis() - t1));
    }*/

    //@Scheduled(cron = "*/60 * * * * ?")
    /*public void test4() throws Exception {

        long t0 = System.currentTimeMillis();
        long metricsRollingStatisticalWindowInMilliseconds = 10000;
        RequestVo requestVo = new RequestVo();

        // 测试时间窗口=metrics.rollingStats.timeInMilliseconds
        while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds) {

            // 模拟不间断请求
            doRequest(requestVo, false);

            // 在metrics.healthSnapshot.intervalInMilliseconds时间达到熔断条件，仍不会启动熔断
            while (System.currentTimeMillis() > t0 + metricsRollingStatisticalWindowInMilliseconds-2000 && System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds-2000+500) {
                doRequest(requestVo, true);
            }
        }
    }*/

    //@Scheduled(cron = "*/60 * * * * ?")
    /*public void test5() throws Exception {
        long t0 = System.currentTimeMillis();
        long metricsRollingStatisticalWindowInMilliseconds = 10000;
        RequestVo requestVo = new RequestVo();

        // 测试时间窗口=metrics.rollingStats.timeInMilliseconds
        while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds) {

            // 模拟不间断请求
            doRequest(requestVo, false);

            // 模拟最后6s，失败次数>5且失败比例>50%启用熔断
            while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds && System.currentTimeMillis() > t0 + metricsRollingStatisticalWindowInMilliseconds-6000) {
                doRequest(requestVo, true);
            }
        }

        // 30s后进行重试，重试成功则熔断关闭
        Thread.sleep(30000);
        t0 = System.currentTimeMillis();
        while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds) {
            doRequest(requestVo, false);
        }
    }*/

    @Scheduled(cron = "*/60 * * * * ?")
    public void test6() throws Exception {
        long t0 = System.currentTimeMillis();
        long metricsRollingStatisticalWindowInMilliseconds = 10000;
        RequestVo requestVo = new RequestVo();

        // 测试时间窗口=metrics.rollingStats.timeInMilliseconds
        while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds) {

            // 模拟不间断请求
            hystrixService.hysOverVolumeThreshold(requestVo.getRequestId(), false);
            requestVo.setRequestId(requestVo.getRequestId()+1);
            Thread.sleep(2000);

            // 模拟最后6s，失败比例>50%但失败次数<5，不会启用熔断
            while (System.currentTimeMillis() < t0 + metricsRollingStatisticalWindowInMilliseconds && System.currentTimeMillis() > t0 + metricsRollingStatisticalWindowInMilliseconds-6000) {
                hystrixService.hysOverVolumeThreshold(requestVo.getRequestId(), true);
                requestVo.setRequestId(requestVo.getRequestId()+1);
                Thread.sleep(2000);
            }
        }

        // 不会启用熔断
        while (true) {
            doRequest(requestVo, false);
        }
    }

    private void doRequest(RequestVo requestVo, Boolean isError) throws Exception {
        hystrixService.hysOverVolumeThreshold(requestVo.getRequestId(), isError);
        requestVo.setRequestId(requestVo.getRequestId()+1);
        Thread.sleep(50);
    }
}
