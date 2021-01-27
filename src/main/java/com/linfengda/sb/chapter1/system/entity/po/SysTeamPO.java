package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.chapter1.common.entity.po.BasePO;
import com.linfengda.sb.support.orm.annontation.Id;
import com.linfengda.sb.support.orm.annontation.Table;
import lombok.Data;

/**
 * @description 系统团队表PO
 * @author linfengda
 * @date 2020-07-27 16:56
 */
@Table(name = "sys_team")
@Data
public class SysTeamPO extends BasePO {
    /**
     * 主键id
     */
    @Id
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
     * 项目类型{@link com.linfengda.sb.chapter1.system.entity.enums.SysTeamType}
     */
    private Integer type;
    /**
     * 状态：0启用，1停用
     */
    private Integer status;
}
