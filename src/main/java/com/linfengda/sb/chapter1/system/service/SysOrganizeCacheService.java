package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;

import java.util.Set;

/**
 * @description: 系统组织缓存服务
 * @author: linfengda
 * @date: 2020-07-27 23:31
 */
public interface SysOrganizeCacheService {

    /**
     * 查询所有部门
     * @return  所有部门
     * @throws Exception
     */
    Set<SysDepartmentDTO> queryDepartments() throws Exception;

    /**
     * 根据id查询部门
     * @param departmentId  部门id
     * @return              部门
     * @throws Exception
     */
    SysDepartmentDTO queryDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id更新部门
     * @param departmentId      部门id
     * @param departmentName    部门名称
     * @param status            状态
     * @return                  部门
     * @throws Exception
     */
    SysDepartmentDTO updateDepartment(Integer departmentId, String departmentName, Integer status) throws Exception;

    /**
     * 根据id删除部门
     * @param departmentId  部门id
     * @throws Exception
     */
    void delDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id查询所有团队
     * @param departmentId  部门id
     * @return              该部门的所有团队
     * @throws Exception
     */
    Set<SysTeamDTO> queryTeamByDepartmentId(Integer departmentId) throws Exception;
}
