package com.linfengda.sb.chapter1.bean.entity;

import com.linfengda.sb.chapter1.bean.type.SysUserStatusType;
import com.linfengda.sb.chapter1.common.bean.po.BaseIncrementEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser extends BaseIncrementEntity<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private SysUserStatusType status;

    @ApiModelProperty(value = "所属部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "所属团队id")
    private Integer teamId;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime lastUpdateTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
