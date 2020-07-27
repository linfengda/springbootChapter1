package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.orm.entity.BasePO;
import com.linfengda.sb.support.orm.tableAnnontation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @description: 系统团队表PO
 * @author: linfengda
 * @date: 2020-07-27 16:56
 */
@Table(name = "sys_team")
@Data
public class SysTeamPO extends BasePO {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 项目名称
     */
    private String teamName;
    /**
     * 项目别名
     */
    private String teamAliasName;
    /**
     * 项目类型，1：技术；2：业务；3：行政
     */
    private Integer type;
    /**
     * 状态：0启用，1停用
     */
    private Integer status;


    /**
     * 项目类型枚举
     */
    @AllArgsConstructor
    @Getter
    public enum Type {
        /**
         * 1：技术
         */
        TECH(1, "技术"),
        /**
         * 2：业务
         */
        BUSINESS(2, "业务"),
        /**
         * 3：行政
         */
        ADMINISTRATION(3, "行政");

        private Integer code;
        private String name;

        public static Type getType(Integer state) {
            for (Type value : values()) {
                if (value.getCode().equals(state)) {
                    return value;
                }
            }
            return null;
        }
    }

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
