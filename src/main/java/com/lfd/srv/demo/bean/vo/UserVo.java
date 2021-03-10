package com.lfd.srv.demo.bean.vo;

import lombok.Data;

/**
 * 描述: 用户详情信息VO
 *
 * @author linfengda
 * @create 2020-01-10 13:39
 */
@Data
public class UserVo {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户状态
     */
    private String status;
}
