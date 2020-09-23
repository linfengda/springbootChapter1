package com.linfengda.sb.chapter1.system.service;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Chapter1Application;
import com.linfengda.sb.chapter1.system.cache.CachePrefix;
import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

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
    private SysOrganizeCacheService sysOrganizeCacheService;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;


    /**
     * 部门缓存查询，新增，修改，删除
     * @throws Exception
     */
    @Test
    public void testDepartmentCache() throws Exception {
        sysOrganizeCacheService.queryDepartment(1);
        SysDepartmentDTO queryDepartmentDTO = genericRedisTemplate.hashGet(CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1");
        log.info("缓存后，根据id查询部门：{}", JSON.toJSONString(queryDepartmentDTO));
        sysOrganizeCacheService.updateDepartment(1, "技术部", 0);
        SysDepartmentDTO updateDepartmentDTO = genericRedisTemplate.hashGet(CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1");
        log.info("更新缓存后，根据id查询部门：{}", JSON.toJSONString(updateDepartmentDTO));
        sysOrganizeCacheService.delDepartment(1);
        SysDepartmentDTO delDepartmentDTO = genericRedisTemplate.hashGet(CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, "1");
        log.info("删除缓存后，根据id查询部门：{}", JSON.toJSONString(delDepartmentDTO));
        sysOrganizeCacheService.queryDepartments();
        Set<SysDepartmentDTO> sysDepartmentDTOSet = genericRedisTemplate.setGetAll(CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE);
        log.info("缓存后，查询所有部门：{}", JSON.toJSONString(sysDepartmentDTOSet));
    }

    /**
     * 团队缓存查询，新增，修改，删除
     * @throws Exception
     */
    @Test
    public void testTeamCache() throws Exception {
        sysOrganizeCacheService.queryTeamByDepartmentId(1);
        Set<SysTeamDTO> sysTeamDTOSet = genericRedisTemplate.setGetAll(CachePrefix.SYS_ORG_TEAM_SET_CACHE + Constant.COLON + "1");
        log.info("缓存后，根据部门id查询所有团队：{}", JSON.toJSONString(sysTeamDTOSet));
    }
}
