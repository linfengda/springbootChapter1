package com.linfengda.sb.chapter1.system.service;

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
     * @param status        部门状态{@link com.linfengda.sb.chapter1.system.entity.po.SysDepartmentPO.Status}
     * @param isDelete      是否删除{@link com.linfengda.sb.support.orm.entity.BasePO.Delete}
     * @return              部门PO
     * @throws Exception
     */
    SysDepartmentPO queryDepartment(Integer departmentId, Integer status, Integer isDelete) throws Exception;

    /**
     * 根据id删除部门
     * @param departmentId  部门id
     * @throws Exception
     */
    void delDepartment(Integer departmentId) throws Exception;
}
