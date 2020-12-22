package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.chapter1.common.entity.po.BasePO;
import com.linfengda.sb.support.orm.annontation.Id;
import com.linfengda.sb.support.orm.annontation.Table;
import lombok.Data;

/**
 * 描述: 系统部门PO
 *
 * @author linfengda
 * @create 2020-03-23 18:07
 */
@Table(name = "sys_department")
@Data
public class SysDepartmentPO extends BasePO {
    /**
     * 主键id
     */
    @Id
    private Integer id;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 部门别名
     */
    private String departmentAliasName;
    /**
     * 部门类型{@link com.linfengda.sb.chapter1.system.entity.enums.SysDepartmentType}
     */
    private Integer type;
    /**
     * 状态：0启用，1停用
     */
    private Integer status;
}