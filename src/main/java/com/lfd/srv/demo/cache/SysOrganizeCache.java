package com.lfd.srv.demo.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lfd.srv.demo.service.SysUserService;
import com.lfd.srv.demo.support.redis.cache.annotation.*;
import com.lfd.srv.demo.bean.dto.SysDepartmentCacheDto;
import com.lfd.srv.demo.bean.dto.SysTeamCacheDto;
import com.lfd.srv.demo.bean.dto.SysUserCacheDto;
import com.lfd.srv.demo.bean.entity.SysDepartment;
import com.lfd.srv.demo.bean.entity.SysTeam;
import com.lfd.srv.demo.bean.entity.SysUser;
import com.lfd.srv.demo.cache.type.SystemCachePrefix;
import com.lfd.srv.demo.service.SysDepartmentService;
import com.lfd.srv.demo.service.SysTeamService;
import com.linfengda.sb.chapter1.support.redis.cache.annotation.*;
import com.lfd.srv.demo.support.redis.cache.entity.type.DataType;
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
    public SysDepartmentCacheDto queryDepartment(@CacheKey Integer departmentId) throws Exception {
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheDto sysDepartmentCacheDTO = new SysDepartmentCacheDto();
        BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheDTO);
        return sysDepartmentCacheDTO;
    }

    @DeleteCache(caches = {@Cache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, allEntries = true)})
    @UpdateCache(type = DataType.HASH, prefix = SystemCachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Transactional(rollbackFor = Exception.class)
    public SysDepartmentCacheDto updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        LambdaUpdateWrapper<SysDepartment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysDepartment::getDepartmentName, departmentName);
        updateWrapper.eq(SysDepartment::getStatus, status);
        sysDepartmentService.update(updateWrapper);
        SysDepartment sysDepartment = sysDepartmentService.getById(departmentId);
        if (null == sysDepartment) {
            return null;
        }
        SysDepartmentCacheDto sysDepartmentCacheDTO = new SysDepartmentCacheDto();
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
    public Set<SysDepartmentCacheDto> queryDepartments() throws Exception {
        List<SysDepartment> sysDepartmentList = sysDepartmentService.list();
        Set<SysDepartmentCacheDto> sysDepartmentCacheDtoSet = new HashSet<>();
        for (SysDepartment sysDepartment : sysDepartmentList) {
            SysDepartmentCacheDto sysDepartmentCacheDTO = new SysDepartmentCacheDto();
            BeanUtils.copyProperties(sysDepartment, sysDepartmentCacheDTO);
            sysDepartmentCacheDtoSet.add(sysDepartmentCacheDTO);
        }
        return sysDepartmentCacheDtoSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysTeamCacheDto> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        LambdaQueryWrapper<SysTeam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTeam::getDepartmentId, departmentId);
        List<SysTeam> sysTeamList = sysTeamService.list(queryWrapper);
        Set<SysTeamCacheDto> sysTeamCacheDtoSet = new HashSet<>();
        for (SysTeam sysTeam : sysTeamList) {
            SysTeamCacheDto sysTeamCacheDTO = new SysTeamCacheDto();
            BeanUtils.copyProperties(sysTeam, sysTeamCacheDTO);
            sysTeamCacheDtoSet.add(sysTeamCacheDTO);
        }
        return sysTeamCacheDtoSet;
    }

    @QueryCache(type = DataType.SET, prefix = SystemCachePrefix.SYS_ORG_USER_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    public Set<SysUserCacheDto> queryUserByTeamId(@CacheKey Integer teamId) throws Exception {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getTeamId, teamId);
        List<SysUser> sysUserList = sysUserService.list(queryWrapper);
        Set<SysUserCacheDto> sysUserCacheDtoSet = new HashSet<>();
        for (SysUser sysUser : sysUserList) {
            SysUserCacheDto sysUserCacheDTO = new SysUserCacheDto();
            BeanUtils.copyProperties(sysUser, sysUserCacheDTO);
            sysUserCacheDtoSet.add(sysUserCacheDTO);
        }
        return sysUserCacheDtoSet;
    }
}
