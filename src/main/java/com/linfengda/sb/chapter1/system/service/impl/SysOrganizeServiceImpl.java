package com.linfengda.sb.chapter1.system.service.impl;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;
import com.linfengda.sb.chapter1.system.service.SysOrganizeCacheService;
import com.linfengda.sb.chapter1.system.service.SysOrganizeService;
import com.linfengda.sb.support.orm.BaseService;
import com.linfengda.sb.support.redis.cache.annotation.DeleteCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private SysOrganizeCacheService sysOrganizeCacheService;


    @Override
    public SysDepartmentPO queryDepartment(Integer departmentId, Integer status) throws Exception {
        SysDepartmentDTO sysDepartmentDTO = sysOrganizeCacheService.queryDepartment(departmentId);
        if (null == sysDepartmentDTO) {
            return null;
        }
        if (null != status && !status.equals(sysDepartmentDTO.getStatus())) {
            return null;
        }
        SysDepartmentPO sysDepartmentPO = new SysDepartmentPO();
        BeanUtils.copyProperties(sysDepartmentDTO, sysDepartmentPO);
        return sysDepartmentPO;
    }

    @Override
    public void delDepartment(Integer departmentId) throws Exception {
        sysOrganizeCacheService.delDepartment(departmentId);
    }

    @Override
    public SysDepartmentDTO updateDepartment(Integer departmentId, String departmentName, Integer status) throws Exception {
        return sysOrganizeCacheService.updateDepartment(departmentId, departmentName, status);
    }
}
