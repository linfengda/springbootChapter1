package com.linfengda.sb.chapter1.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linfengda.sb.support.mybatis.annotation.BizTimeField;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 描述: 用户信息VO
 *
 * @author linfengda
 * @create 2018-08-13 22:06
 */
@Data
public class UserListVo {
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
     * 创建人
     */
    private Long createUser;
    /**
     * 创建时间
     */
    @BizTimeField
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;
}
