package com.linfengda.sb.chapter1.system.entity.dto;

import com.linfengda.sb.chapter1.common.entity.dto.BasePageDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 描述: 用户分页查询DTO
 *
 * @author linfengda
 * @create 2019-12-19 13:27
 */
@Data
public class UserPageQueryDTO extends BasePageDTO {
    /**
     * 用户状态
     */
    @NotNull(message = "用户状态不能为空")
    private Integer status;
    /**
     * 用户名称
     */
    private String userName;
}
