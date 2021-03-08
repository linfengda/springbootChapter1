package com.linfengda.sb.chapter1.entity.po;

import com.linfengda.sb.chapter1.common.bean.po.BaseIncrementEntity;
import com.linfengda.sb.support.orm.annontation.Id;
import com.linfengda.sb.support.orm.annontation.Table;
import lombok.Data;

/**
 * 描述: 系统用户PO
 *
 * @author linfengda
 * @create 2019-01-03 16:15
 */
@Table(name = "sys_user")
@Data
public class SysUserIncrementEntity extends BaseIncrementEntity {
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
}