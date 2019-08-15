package com.linfengda.sb.chapter1.system.entity.dto;

import lombok.Data;

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
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
}
