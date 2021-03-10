package com.lfd.soa.srv.demo.support.orm.entity;

import com.lfd.soa.srv.demo.support.orm.annontation.Table;
import com.lfd.soa.srv.demo.support.orm.auto.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户PO
 *
 * @author linfengda
 * @date 2021-03-09 11:49
 */
@Table(name = "sys_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SysUserPO extends BaseEntity {

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private String status;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "所属团队id")
    private Integer teamId;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;
}
