package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.common.cache.bo.SysDepartmentBO;
import com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 13:39
 */
public interface SysOrganizeService {

    /**
     * 根据id查询部门
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    SysDepartmentPO queryDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id查询部门并缓存
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    SysDepartmentBO cacheDepartment(Integer departmentId) throws Exception;
}
