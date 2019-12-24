package com.linfengda.sb.chapter1.system.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 描述: 用户DTO
 *
 * @author linfengda
 * @create 2019-07-28 15:12
 */
@Data
public class UserDTO {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 用户名称
     */
    @NotNull(message = "用户名称")
    private String userName;
}
