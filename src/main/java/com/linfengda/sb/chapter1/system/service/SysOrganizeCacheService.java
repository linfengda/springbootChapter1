package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentCacheDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamCacheDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysUserCacheDTO;

import java.util.Set;

/**
 * @description 系统组织缓存服务
 * @author linfengda
 * @date 2020-07-27 23:31
 */
public interface SysOrganizeCacheService {

    /**
     * 根据id查询部门
     * @param departmentId  部门id
     * @return              部门
     * @throws Exception
     */
    SysDepartmentCacheDTO queryDepartment(Integer departmentId) throws Exception;

    /**
     * 根据id更新部门
     * @param departmentId      部门id
     * @param departmentName    部门名称
     * @param status            状态
     * @return                  部门
     * @throws Exception
     */
    SysDepartmentCacheDTO updateDepartment(Integer departmentId, String departmentName, Integer status) throws Exception;

    /**
     * 根据id删除部门
     * @param departmentId  部门id
     * @throws Exception
     */
    void delDepartment(Integer departmentId) throws Exception;

    /**
     * 查询所有部门
     * @return  所有部门
     * @throws Exception
     */
    Set<SysDepartmentCacheDTO> queryDepartments() throws Exception;

    /**
     * 根据id查询所有团队
     * @param departmentId  部门id
     * @return              该部门的所有团队
     * @throws Exception
     */
    Set<SysTeamCacheDTO> queryTeamByDepartmentId(Integer departmentId) throws Exception;

    /**
     * 根据id查询所有员工
     * @param teamId        团队id
     * @return              该团队的所有员工
     * @throws Exception
     */
    Set<SysUserCacheDTO> queryUserByTeamId(Integer teamId) throws Exception;
}
