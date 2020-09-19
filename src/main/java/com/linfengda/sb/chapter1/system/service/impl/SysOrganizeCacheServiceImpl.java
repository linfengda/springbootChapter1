package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.common.cache.CachePrefix;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description: 系统组织缓存服务
 * @author: linfengda
 * @date: 2020-07-27 23:32
 */
@Service
@Slf4j
public class SysOrganizeCacheServiceImpl extends BaseService implements SysOrganizeCacheService {


    @QueryCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE, timeOut = 7, timeUnit = TimeUnit.DAYS)
    @Override
    public SysDepartmentDTO queryDepartment(Integer departmentId) throws Exception {
        SysDepartmentPO sysDepartmentPO = findByPrimaryKey(departmentId, SysDepartmentPO.class);
        if (null == sysDepartmentPO) {
            return null;
        }
        SysDepartmentDTO sysDepartmentDTO = new SysDepartmentDTO();
        sysDepartmentDTO.setId(sysDepartmentPO.getId());
        sysDepartmentDTO.setDepartmentName(sysDepartmentPO.getDepartmentName());
        sysDepartmentDTO.setDepartmentAliasName(sysDepartmentPO.getDepartmentAliasName());
        sysDepartmentDTO.setType(sysDepartmentPO.getType());
        sysDepartmentDTO.setStatus(sysDepartmentPO.getStatus());
        return sysDepartmentDTO;
    }

    @DeleteCache(type = DataType.HASH, prefix = CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE)
    @Override
    public void delDepartment(Integer departmentId) throws Exception {
        // TODO 逻辑删除部门并同时删除缓存
    }
}
