package com.linfengda.sb.chapter1.demo.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.entity.vo.UserVO;
import com.linfengda.sb.support.dao.OrmTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * 描述: MES ORM框架测试
 *
 * @author linfengda
 * @create 2020-01-13 15:10
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ORMFrameworkSpringBootTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Resource
    OrmTemplate ormTemplate;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉cn.dotfashion.MesApplication类的@EnableApplicationStartup注解！");
    }

    @Test
    @Rollback(true)
    @Transactional(rollbackFor = Exception.class)
    public void testQuery() throws Exception {
        SysUserPO sysUserPO = ormTemplate.findByPrimaryKey(1, SysUserPO.class);
        UserVO userVO = new UserVO();
        userVO.setUserId(sysUserPO.getUserId());
        userVO.setUserName(sysUserPO.getUserName());
        userVO.setPhone(sysUserPO.getPhone());
        userVO.setPassword(sysUserPO.getPassword());
        userVO.setStatus(sysUserPO.getStatus());
        log.info("查询用户信息={}", JSON.toJSONString(userVO));
    }
}
