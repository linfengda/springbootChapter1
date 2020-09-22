package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;

/**
 * @description: 系统组织缓存服务
 * @author: linfengda
 * @date: 2020-07-27 23:31
 */
public interface SysOrganizeCacheService {

    /**
     * 根据id查询部门
     * @param departmentId  部门id
     * @return
     * @throws Exception
     */
    SysDepartmentDTO queryDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id删除部门
     * @param departmentId  部门id
     * @throws Exception
     */
    void delDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id更新部门
     * @param departmentId      部门id
     * @param departmentName    部门名称
     * @param status            状态
     * @return
     * @throws Exception
     */
    SysDepartmentDTO updateDepartment(Integer departmentId, String departmentName, Integer status) throws Exception;
}
