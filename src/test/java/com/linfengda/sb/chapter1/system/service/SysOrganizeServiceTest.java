package com.linfengda.sb.chapter1.system.service;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

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
    private SysOrganizeCacheService sysOrganizeCacheService;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    /**
     * 测试部门缓存查询，删除
     * @throws Exception
     */
    @Test
    public void testDepartmentCache() throws Exception {
        SysDepartmentDTO sysDepartmentDTO = sysOrganizeService.queryDepartment(1, 0);
        log.info("根据id查询部门：{}", JSON.toJSONString(sysDepartmentDTO));
        sysOrganizeCacheService.updateDepartment(1, "技术部", 0);
        SysDepartmentDTO updateDepartmentDTO = sysOrganizeService.queryDepartment(1, 0);
        log.info("更新缓存后，查询部门：{}", JSON.toJSONString(updateDepartmentDTO));
        sysOrganizeCacheService.delDepartment(1);
        SysDepartmentDTO delDepartmentDTO = genericRedisTemplate.hashGet("sys:org:pt", "1");
        log.info("删除缓存后，查询部门：{}", JSON.toJSONString(delDepartmentDTO));

        List<SysTeamDTO> sysTeamDTOList = sysOrganizeService.queryTeamByDepartmentId(1, 0);
        log.info("根据id查询所有团队：{}", JSON.toJSONString(sysTeamDTOList));
    }
}
