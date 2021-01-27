package com.linfengda.sb.support.redis.test;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.common.thread.ThreadPoolHelper;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @description redis缓存注解测试
 * @author linfengda
 * @date 2020-09-17 15:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisCacheAnnotationTest {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


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
    public void testNullKeyCache() throws Exception {
        log.info("{}", JSON.toJSONString(sysUserService.getDepartmentUserList(-1)));
        log.info("{}", JSON.toJSONString(sysUserService.getTeamUserList(-1)));
        log.info("{}", JSON.toJSONString(sysUserService.getUserInfo(-1)));
    }

    /**
     * 测试lru缓存
     * @throws Exception
     */
    @Test
    public void testLruCache() throws Exception {
        log.info("测试lru缓存开始");
        for (int i = 1; i < 10; i++) {
            UserVO userVO = sysUserService.getUserInfo(i);
            log.info("用户查询：{}", JSON.toJSONString(userVO));
            Set<String> userKeySet = genericRedisTemplate.hashKeys("sys:user");
            log.info("当前lru缓存：{}", JSON.toJSONString(userKeySet));
        }
        log.info("测试lru缓存结束");
    }

    /**
     * 测试更新缓存
     * @throws Exception
     */
    @Test
    public void testUpdateCache() throws Exception {
        log.info("更新用户信息前：{}", JSON.toJSONString(sysUserService.getUserInfo(9)));
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setUserId(9);
        updateDTO.setUserName("新的开始林天天");
        sysUserService.updateUserInfo(9, updateDTO);
        log.info("更新用户信息后：{}", JSON.toJSONString(sysUserService.getUserInfo(9)));
    }
}
