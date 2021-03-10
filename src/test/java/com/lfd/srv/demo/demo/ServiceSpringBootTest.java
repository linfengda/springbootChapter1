package com.lfd.srv.demo.demo;

import com.lfd.srv.demo.service.SysUserService;
import com.lfd.srv.demo.Chapter1Application;
import com.lfd.srv.demo.cache.UserTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: 单单只是服务层测试
 *
 * @author linfengda
 * @create 2020-01-13 18:04
 */
@ActiveProfiles("dev")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceSpringBootTest {
    @Autowired
    private SysUserService sysUserService;
    //使用mock包装的bean，对bean调用的方法进行模拟
    @MockBean
    private UserTokenCache userTokenCache;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉@EnableApplicationStartup注解！");
    }

    /**
     * 测试一个service方法
     * @throws Exception
     */
    @Test
    @Rollback(true)
    @Transactional(rollbackFor = Exception.class)
    public void test1() throws Exception {
        // 假设未连接redis，模拟缓存方法调用
        Mockito.doNothing().when(userTokenCache).clearCache();
        sysUserService.getUserInfo(1);
    }
}
