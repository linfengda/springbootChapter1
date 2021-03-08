package com.linfengda.sb.chapter1.bean.entity;

import com.linfengda.sb.chapter1.bean.type.SysDepartmentStatusType;
import com.linfengda.sb.chapter1.bean.type.SysDepartmentType;
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
 * 部门表
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SysDepartment对象", description="部门表")
public class SysDepartment extends BaseIncrementEntity<SysDepartment> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门别名")
    private String departmentAliasName;

    @ApiModelProperty(value = "类型，1：技术；2：业务；3：行政")
    private SysDepartmentType type;

    @ApiModelProperty(value = "状态，0：启用，1:停用")
    private SysDepartmentStatusType status;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime lastUpdateTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
