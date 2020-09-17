package com.linfengda.sb.support.middleware.redis.performance.test;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.common.thread.ThreadPoolHelper;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @description: redis缓存注解测试
 * @author: linfengda
 * @date: 2020-09-17 15:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisCacheAnnotationTest {
    @Resource
    private SysUserService sysUserService;


    /**
     * 测试多个线程同时更新缓存
     * @throws Exception
     */
    @Test
    public void testMultiQueryCache() throws Exception {
        ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(10, 20, "test-thread");
        CountDownLatch startCount = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try{
                    startCount.await();
                    UserVO userVO = sysUserService.getUserInfo(1);
                    log.info("userVO={}", JSON.toJSONString(userVO));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            });
            startCount.countDown();
        }
        while(true) {
            Thread.sleep(60000);
        }
    }

    /**
     * 测试缓存空值
     * @throws Exception
     */
    @Test
    public void testCacheNullKey() throws Exception {
        log.info("{}", JSON.toJSONString(sysUserService.getTeamUserList(-1)));
        log.info("{}", JSON.toJSONString(sysUserService.getUserInfo(-1)));
    }

    /**
     * 测试LRU缓存
     * @throws Exception
     */
    @Test
    public void testLruCache() throws Exception {
        for (int i = 1; i <= 10; i++) {
            UserVO userVO = sysUserService.getUserInfo(i);
            log.info("{}", JSON.toJSONString(userVO));
        }
    }
}
