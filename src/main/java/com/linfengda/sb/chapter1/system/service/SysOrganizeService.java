package com.linfengda.sb.chapter1.system.service;

import com.linfengda.sb.chapter1.system.entity.dto.SysDepartmentDTO;
import com.linfengda.sb.chapter1.system.entity.dto.SysTeamDTO;

import java.util.List;

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
     * @param status        部门状态{@link com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO.Status}
     * @return              部门
     * @throws Exception
     */
    SysDepartmentDTO queryDepartment(Integer departmentId, Integer status) throws Exception;

    /**
     * 根据id查询所有团队
     * @param departmentId  部门id
     * @param status        团队状态{@link com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO.Status}
     * @return              该部门的所有团队
     * @throws Exception
     */
    List<SysTeamDTO> queryTeamByDepartmentId(Integer departmentId, Integer status) throws Exception;
}
