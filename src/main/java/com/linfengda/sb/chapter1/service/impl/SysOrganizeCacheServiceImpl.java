package com.linfengda.sb.chapter1.service.impl;

import com.linfengda.sb.chapter1.bean.dto.SysDepartmentCacheDTO;
import com.linfengda.sb.chapter1.bean.dto.SysTeamCacheDTO;
import com.linfengda.sb.chapter1.bean.dto.SysUserCacheDTO;
import com.linfengda.sb.chapter1.cache.SystemCachePrefix;
import com.linfengda.sb.chapter1.service.SysOrganizeCacheService;
import com.linfengda.sb.support.orm.AbstractBaseService;
import com.linfengda.sb.support.redis.cache.annotation.*;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description 系统组织缓存服务
 * @author linfengda
 * @date 2020-07-27 23:32
 */
@Service
@Slf4j
public class SysOrganizeCacheServiceImpl extends AbstractBaseService implements SysOrganizeCacheService {


    @QueryCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentCacheDTO queryDepartment(@CacheKey Integer departmentId) throws Exception {
        /*SysDepartmentIncrementEntity sysDepartmentPO = getByPrimaryKey(departmentId, SysDepartmentIncrementEntity.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;*/
        return null;
    }

    @DeleteCache(caches = {@Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true)})
    @UpdateCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDepartmentCacheDTO updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        /*SetValue setValue = new SetValue();
        setValue.add("departmentName", departmentName);
        setValue.add("status", status);
        updateByPrimaryKey(SysDepartmentIncrementEntity.class, setValue, departmentId);
        SysDepartmentIncrementEntity sysDepartmentPO = getByPrimaryKey(departmentId, SysDepartmentIncrementEntity.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;*/
        return null;
    }

    @DeleteCache(
            caches = {
            @Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true),
            @Cache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)})
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delDepartment(@CacheKey Integer departmentId) throws Exception {
        /*SetValue setValue = new SetValue();
        setValue.add("deleted", BaseIncrementEntity.Deleted.DELETED.getCode());
        updateByPrimaryKey(SysDepartmentIncrementEntity.class, setValue, departmentId);*/
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysDepartmentCacheDTO> queryDepartments() throws Exception {
        /*ConditionParam conditionParam = new ConditionParam();
        List<SysDepartmentIncrementEntity> sysDepartmentPOList = query(conditionParam, SysDepartmentIncrementEntity.class);
        Set<SysDepartmentCacheDTO> sysDepartmentCacheDTOSet = new HashSet<>();
        for (SysDepartmentIncrementEntity sysDepartmentPO : sysDepartmentPOList) {
            SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
            BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentCacheDTO);
            sysDepartmentCacheDTOSet.add(sysDepartmentCacheDTO);
        }
        return sysDepartmentCacheDTOSet;*/
        return null;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysTeamCacheDTO> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        /*ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", departmentId);
        List<SysTeamIncrementEntity> sysTeamPOList = query(conditionParam, SysTeamIncrementEntity.class);
        Set<SysTeamCacheDTO> sysTeamCacheDTOSet = new HashSet<>();
        for (SysTeamIncrementEntity sysTeamPO : sysTeamPOList) {
            SysTeamCacheDTO sysTeamCacheDTO = new SysTeamCacheDTO();
            BeanUtils.copyProperties(sysTeamPO, sysTeamCacheDTO);
            sysTeamCacheDTOSet.add(sysTeamCacheDTO);
        }
        return sysTeamCacheDTOSet;*/
        return null;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_USER_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysUserCacheDTO> queryUserByTeamId(@CacheKey Integer teamId) throws Exception {
        /*ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("teamId", teamId);
        List<SysUserIncrementEntity> sysUserPOList = query(conditionParam, SysUserIncrementEntity.class);
        Set<SysUserCacheDTO> sysUserCacheDTOSet = new HashSet<>();
        for (SysUserIncrementEntity sysUserPO : sysUserPOList) {
            SysUserCacheDTO sysUserCacheDTO = new SysUserCacheDTO();
            BeanUtils.copyProperties(sysUserPO, sysUserCacheDTO);
            sysUserCacheDTOSet.add(sysUserCacheDTO);
        }
        return sysUserCacheDTOSet;*/
        return null;
    }
}
