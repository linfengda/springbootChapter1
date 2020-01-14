package com.linfengda.sb.chapter1.system.service;

/**
 * 描述: 系统组织关系服务
 *
 * @author linfengda
 * @create 2020-01-14 13:39
 */
public interface SysOrganizeService {

    /**
     * 删除部门
     * @param departmentId
     * @throws Exception
     */
    void delDepartment(Integer departmentId) throws Exception;

    /**
     * 删除团队
     * @param teamId
     * @throws Exception
     */
    void delTeam(Integer teamId) throws Exception;
}
