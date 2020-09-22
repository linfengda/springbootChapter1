package com.linfengda.sb.chapter1.system.service;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
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
 * @description:
 * @author: linfengda
 * @date: 2020-09-22 23:00
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Chapter1Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysOrganizeServiceTest {
    @Resource
    private SysOrganizeService sysOrganizeService;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    /**
     * 测试部门缓存查询，删除
     * @throws Exception
     */
    @Test
    public void testDepartmentCache() throws Exception {
        SysDepartmentPO sysDepartmentPO = sysOrganizeService.queryDepartment(1, 0);
        log.info("查询后，缓存查询部门：{}", JSON.toJSONString(sysDepartmentPO));
        sysOrganizeService.updateDepartment(1, "技术部", 0);
        SysDepartmentPO updateDepartmentPO = sysOrganizeService.queryDepartment(1, 0);
        log.info("更新缓存后，查询部门：{}", JSON.toJSONString(updateDepartmentPO));
        sysOrganizeService.delDepartment(1);
        SysDepartmentPO delDepartmentPO = genericRedisTemplate.hashGet("sys:org:pt", "1");
        log.info("删除缓存后，查询部门：{}", JSON.toJSONString(delDepartmentPO));
    }
}
