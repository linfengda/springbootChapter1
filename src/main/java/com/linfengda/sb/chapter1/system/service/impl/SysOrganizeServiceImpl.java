package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.cache.OrganizeCache;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.support.dao.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 14:10
 */
@Service
@Slf4j
public class SysOrganizeServiceImpl extends BaseService implements SysOrganizeService {
    @Resource
    private OrganizeCache organizeCache;

    @Override
    public void delDepartment(Integer departmentId) throws Exception {
        log.info("删除部门");
        organizeCache.clearCache();
    }

    @Override
    public void delTeam(Integer teamId) throws Exception {
        log.info("删除团队");
        organizeCache.clearCache();
    }
}
