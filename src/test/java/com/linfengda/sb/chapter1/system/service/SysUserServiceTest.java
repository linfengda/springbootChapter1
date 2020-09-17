package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
public class SysUserServiceTest {
    @Resource
    private SysUserService sysUserService;

    @Before
    public void setup() throws Exception {
        log.info("注意：若不需要程序初始化，去掉@EnableApplicationStartup注解！");
    }

    /**
     * 测试LRU缓存的查询，删除
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setUserId(1);
        userUpdateDTO.setUserName("xxx");
        sysUserService.updateUserInfo(1, userUpdateDTO);
    }
}
