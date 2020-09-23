package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.cache.CachePrefix;
import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.entity.po.SysTeamPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.redis.cache.annotation.CacheKey;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
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


    @QueryCache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysDepartmentDTO> queryDepartments() throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        List<SysDepartmentPO> sysDepartmentPOList = findAll(conditionParam, SysDepartmentPO.class);
        Set<SysDepartmentDTO> sysDepartmentDTOSet = new HashSet<>();
        for (SysDepartmentPO sysDepartmentPO : sysDepartmentPOList) {
            SysDepartmentDTO sysDepartmentDTO = new SysDepartmentDTO();
            BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentDTO);
            sysDepartmentDTOSet.add(sysDepartmentDTO);
        }
        return sysDepartmentDTOSet;
    }

    @QueryCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentDTO queryDepartment(@CacheKey Integer departmentId) throws Exception {
        SysDepartmentPO sysDepartmentPO = findByPrimaryKey(departmentId, SysDepartmentPO.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentDTO sysDepartmentDTO = new SysDepartmentDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentDTO);
        return sysDepartmentDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @UpdateCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentDTO updateDepartment(@CacheKey Integer departmentId, String departmentName, Integer status) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("departmentName", departmentName);
        setValue.add("status", status);
        updateByPrimaryKey(SysDepartmentPO.class, setValue, departmentId);
        SysDepartmentPO sysDepartmentPO = findByPrimaryKey(departmentId, SysDepartmentPO.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentDTO sysDepartmentDTO = new SysDepartmentDTO();
        BeanUtils.copyProperties(sysDepartmentPO, sysDepartmentDTO);
        return sysDepartmentDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)
    @Override
    public void delDepartment(@CacheKey Integer departmentId) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("isDelete", SysDepartmentPO.Delete.DELETED.getCode());
        updateByPrimaryKey(SysDepartmentPO.class, setValue, departmentId);
    }

    @QueryCache(type = DataType.SET, prefix = CachePrefix.SYS_ORG_TEAM_SET_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public Set<SysTeamDTO> queryTeamByDepartmentId(@CacheKey Integer departmentId) throws Exception {
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add("departmentId", departmentId);
        List<SysTeamPO> sysTeamPOList = findAll(conditionParam, SysTeamPO.class);
        Set<SysTeamDTO> sysTeamDTOSet = new HashSet<>();
        for (SysTeamPO sysTeamPO : sysTeamPOList) {
            SysTeamDTO sysTeamDTO = new SysTeamDTO();
            BeanUtils.copyProperties(sysTeamPO, sysTeamDTO);
            sysTeamDTOSet.add(sysTeamDTO);
        }
        return sysTeamDTOSet;
    }
}
