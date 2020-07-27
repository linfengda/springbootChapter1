package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.orm.entity.BasePO;
import com.linfengda.sb.support.orm.tableAnnontation.Id;
import com.linfengda.sb.support.orm.tableAnnontation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 描述: 系统用户PO
 *
 * @author linfengda
 * @create 2019-01-03 16:15
 */
@Table(name = "sys_user")
@Data
public class SysUserPO extends BasePO {
    /**
     * 主键id
     */
    @Id
    private Integer id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 项目id
     */
    private Integer teamId;


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