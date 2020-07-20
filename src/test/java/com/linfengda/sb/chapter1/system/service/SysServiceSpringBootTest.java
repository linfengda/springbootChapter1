package com.linfengda.sb.chapter1.system.service;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.common.thread.ThreadPoolHelper;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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
 * 描述: 系统组织用户测试
 *
 * @author: linfengda
 * @date: 2020-07-14 11:36
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysServiceSpringBootTest {
    @Resource
    private SysUserService sysUserService;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉@EnableApplicationStartup注解！");
    }

    /**
     * 测试多线程查询缓存方法
     * @throws Exception
     */
    @Test
    public void testMultiQueryCacheMethod() throws Exception {
        ThreadPoolTaskExecutor executor = ThreadPoolHelper.initThreadPool(10, 20);
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
     * 测试LRU缓存
     * @throws Exception
     */
    @Test
    public void testLruCache() throws Exception {
        for (int i = 1; i <= 11; i++) {
            UserVO userVO = sysUserService.getUserInfo(i);
            log.info("{}", JSON.toJSONString(userVO));
        }
    }
}
