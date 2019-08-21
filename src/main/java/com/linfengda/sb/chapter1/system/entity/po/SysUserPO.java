package com.linfengda.sb.chapter1.system.entity.po;

import com.linfengda.sb.support.dao.entity.BasePO;
import com.linfengda.sb.support.dao.tableAnnontation.Id;
import com.linfengda.sb.support.dao.tableAnnontation.Table;
import lombok.Data;

/**
 * 描述: 系统用户
 *
 * @author linfengda
 * @create 2019-01-03 16:15
 */
@Table(name = "sys_user")
@Data
public class SysUserPO extends BasePO {
    /**
     * 主键ID
     */
    @Id
    private Long userId;
    /**
     * 用户编码
     */
    private String userCode;
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
}