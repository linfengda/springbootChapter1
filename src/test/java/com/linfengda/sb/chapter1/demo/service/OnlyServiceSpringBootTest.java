package com.linfengda.sb.chapter1.demo.service;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.common.cache.OrganizeCache;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述: 单单只是服务层测试
 *
 * @author linfengda
 * @create 2020-01-13 18:04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OnlyServiceSpringBootTest {
    @Autowired
    private SysOrganizeService sysOrganizeService;
    //使用mock包装的bean，对bean调用的方法进行模拟
    @MockBean
    private OrganizeCache organizeCache;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉cn.dotfashion.MesApplication类的@EnableApplicationStartup注解！");
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
        Mockito.doNothing().when(organizeCache).clearCache();
        sysOrganizeService.delDepartment(123);
        sysOrganizeService.delTeam(456);
    }
}
