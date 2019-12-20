package com.linfengda.sb.chapter1.common.api.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 描述: 基础分页DTO
 *
 * @author linfengda
 * @create 2019-12-18 10:06
 */
@Data
public class BasePageDTO {
    /**
     * 分页码数
     */
    @NotNull(message = "分页页码必须为数字且不能为空")
    private Integer pageNo;
    /**
     * 分页大小
     */
    @NotNull(message = "分页大小必须为数字且不能为空")
    private Integer pageSize;
}
