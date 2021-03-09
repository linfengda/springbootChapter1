package com.linfengda.sb.chapter1.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linfengda.sb.chapter1.bean.dto.SysDepartmentCacheDTO;
import com.linfengda.sb.chapter1.bean.dto.SysTeamCacheDTO;
import com.linfengda.sb.chapter1.bean.dto.SysUserCacheDTO;
import com.linfengda.sb.chapter1.bean.entity.SysDepartment;
import com.linfengda.sb.chapter1.bean.entity.SysTeam;
import com.linfengda.sb.chapter1.bean.entity.SysUser;
import com.linfengda.sb.chapter1.cache.type.SystemCachePrefix;
import com.linfengda.sb.chapter1.service.SysDepartmentService;
import com.linfengda.sb.chapter1.service.SysTeamService;
import com.linfengda.sb.chapter1.service.SysUserService;
import com.linfengda.sb.support.redis.cache.annotation.*;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description 系统组织缓存服务
 * @author linfengda
 * @date 2020-07-27 23:32
 */
@Component
@Slf4j
public class SysOrganizeCache {
    @Resource
    private SysDepartmentService sysDepartmentService;
    @Resource
    private SysTeamService sysTeamService;
    @Resource
    private SysUserService sysUserService;


    @QueryCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public SysDepartmentCacheDTO queryDepartment(@CacheKey Integer departmentId) throws Exception {
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;
    }

    @DeleteCache(caches = {@Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true)})
    @UpdateCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Transactional(rollbackFor = Exception.class)
    public SysDepartmentCacheDTO updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        LambdaUpdateWrapper<SysDepartment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDepartment::getDepartmentName, departmentName);
        updateWrapper.eq(SysDepartment::getStatus, status);
        sysDepartmentService.update(updateWrapper);
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
        BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;
    }

    @DeleteCache(
            caches = {
            @Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true),
            @Cache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)})
    @Transactional(rollbackFor = Exception.class)
    public void delDepartment(@CacheKey Integer departmentId) throws Exception {
        sysDepartmentService.removeById(departmentId);
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysDepartmentCacheDTO> queryDepartments() throws Exception {
        List<SysDepartment> sysDepartmentList = sysDepartmentService.list();
        Set<SysDepartmentCacheDTO> sysDepartmentCacheDTOSet = new HashSet<>();
        for (SysDepartment sysDepartment : sysDepartmentList) {
            SysDepartmentCacheDTO sysDepartmentCacheDTO = new SysDepartmentCacheDTO();
            BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheDTO);
            sysDepartmentCacheDTOSet.add(sysDepartmentCacheDTO);
        }
        return sysDepartmentCacheDTOSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysTeamCacheDTO> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        LambdaQueryWrapper<SysTeam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTeam::getDepartmentId, departmentId);
        List<SysTeam> sysTeamList = sysTeamService.list(queryWrapper);
        Set<SysTeamCacheDTO> sysTeamCacheDTOSet = new HashSet<>();
        for (SysTeam sysTeam : sysTeamList) {
            SysTeamCacheDTO sysTeamCacheDTO = new SysTeamCacheDTO();
            BeanUtils.copyProperties(sysTeam, sysTeamCacheDTO);
            sysTeamCacheDTOSet.add(sysTeamCacheDTO);
        }
        return sysTeamCacheDTOSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_USER_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysUserCacheDTO> queryUserByTeamId(@CacheKey Integer teamId) throws Exception {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getTeamId, teamId);
        List<SysUser> sysUserList = sysUserService.list(queryWrapper);
        Set<SysUserCacheDTO> sysUserCacheDTOSet = new HashSet<>();
        for (SysUser sysUser : sysUserList) {
            SysUserCacheDTO sysUserCacheDTO = new SysUserCacheDTO();
            BeanUtils.copyProperties(sysUser, sysUserCacheDTO);
            sysUserCacheDTOSet.add(sysUserCacheDTO);
        }
        return sysUserCacheDTOSet;
    }
}
