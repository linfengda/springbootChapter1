package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.cache.CachePrefix;
import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentCacheDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamCacheDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysUserCacheDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.entity.po.SysTeamPO;
import com.linfengda.sb.chapter1.system.entity.po.SysUserPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.redis.cache.annotation.*;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: 系统组织缓存服务
 * @author: linfengda
 * @date: 2020-07-27 23:32
 */
@Service
@Slf4j
public class SysOrganizeCacheServiceImpl extends BaseService implements SysOrganizeCacheService {


    @QueryCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentCacheDTO queryDepartment(@CacheKey Integer departmentId) throws Exception {
        SysDepartmentPO sysDepartmentPO = findByPrimaryKey(departmentId, SysDepartmentPO.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteCache(caches = {@Cache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true)})
    @UpdateCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentCacheDTO updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("departmentName", departmentName);
        setValue.add("status", status);
        updateByPrimaryKey(SysDepartmentPO.class, setValue, departmentId);
        SysDepartmentPO sysDepartmentPO = findByPrimaryKey(departmentId, SysDepartmentPO.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;
    }

    @DeleteCache(caches = {
            @Cache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true),
            @Cache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delDepartment(@CacheKey Integer departmentId) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("isDelete", SysDepartmentPO.Delete.DELETED.getCode());
        updateByPrimaryKey(SysDepartmentPO.class, setValue, departmentId);
    }

    @QueryCache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysDepartmentCacheDTO> queryDepartments() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        List<SysDepartmentPO> sysDepartmentPOList = findAll(conditionParam, SysDepartmentPO.class);
        Set<SysDepartmentCacheDTO> sysDepartmentCacheDTOSet = new HashSet<>();
        for (SysDepartmentPO sysDepartmentPO : sysDepartmentPOList) {
            SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
            BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
            sysDepartmentCacheDTOSet.add(sysDepartmentCacheDTO);
        }
        return sysDepartmentCacheDTOSet;
    }

    @QueryCache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysTeamCacheDTO> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", departmentId);
        List<SysTeamPO> sysTeamPOList = findAll(conditionParam, SysTeamPO.class);
        Set<SysTeamCacheDTO> sysTeamCacheDTOSet = new HashSet<>();
        for (SysTeamPO sysTeamPO : sysTeamPOList) {
            SysTeamCacheDTO sysTeamCacheDTO = new SysTeamCacheDTO();
            BeanUtils.copyProperties(sysTeamPO, sysTeamCacheDTO);
            sysTeamCacheDTOSet.add(sysTeamCacheDTO);
        }
        return sysTeamCacheDTOSet;
    }

    @QueryCache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_USER_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysUserCacheDTO> queryUserByTeamId(@CacheKey Integer teamId) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("teamId", teamId);
        List<SysUserPO> sysUserPOList = findAll(conditionParam, SysUserPO.class);
        Set<SysUserCacheDTO> sysUserCacheDTOSet = new HashSet<>();
        for (SysUserPO sysUserPO : sysUserPOList) {
            SysUserCacheDTO sysUserCacheDTO = new SysUserCacheDTO();
            BeanUtils.copyProperties(sysUserPO, sysUserCacheDTO);
            sysUserCacheDTOSet.add(sysUserCacheDTO);
        }
        return sysUserCacheDTOSet;
    }
}
