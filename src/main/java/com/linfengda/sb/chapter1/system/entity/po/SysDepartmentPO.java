package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.orm.entity.BasePO;
import lombok.Data;

/**
 * 描述: 系统部门PO
 *
 * @author linfengda
 * @create 2020-03-23 18:07
 */
@Data
public class SysDepartmentPO extends BasePO {
    /**
     * 主键id
     */
    private Integer departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门别名
     */
    private String departmentAliasName;

    /**
     * 部门类型（1：技术；2：业务；3：行政）
     */
    private Boolean type;

    /**
     * 状态：0启用，1停用
     */
    private Boolean status;
}