package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.common.cache.CachePrefix;
import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.annotation.UpdateCache;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SysDepartmentDTO queryDepartment(Integer departmentId) throws Exception {
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
    public void delDepartment(Integer departmentId) throws Exception {
        SetValue setValue = new SetValue();
        setValue.add("isDelete", SysDepartmentPO.Delete.DELETED.getCode());
        updateByPrimaryKey(SysDepartmentPO.class, setValue, departmentId);
    }

    @Transactional(rollbackFor = Exception.class)
    @UpdateCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentDTO updateDepartment(Integer departmentId, String departmentName, Integer status) throws Exception {
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
}
