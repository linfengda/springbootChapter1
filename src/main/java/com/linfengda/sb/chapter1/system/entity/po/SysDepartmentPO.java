package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.orm.entity.base.BasePO;
import com.linfengda.sb.support.orm.tableAnnontation.Id;
import com.linfengda.sb.support.orm.tableAnnontation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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
     * 部门类型{@link com.linfengda.sb.chapter1.system.entity.type.SysDepartmentType}
     */
    private Integer type;
    /**
     * 状态：0启用，1停用
     */
    private Integer status;


    /**
     * 状态枚举
     */
    @AllArgsConstructor
    @Getter
    public enum Status {
        /**
         * 0：启用
         */
        YES(0, "启用"),
        /**
         * 1：停用
         */
        NO(1, "停用");

        private Integer code;
        private String name;

        public static Status getType(Integer state) {
            for (Status value : values()) {
                if (value.getCode().equals(state)) {
                    return value;
                }
            }
            return null;
        }
    }
}