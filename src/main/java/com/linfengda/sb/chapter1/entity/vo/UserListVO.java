package com.linfengda.sb.chapter1.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.linfengda.sb.support.serializable.jackson.date.TimeFormatMdSerializer;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 描述: 用户信息VO
 *
 * @author linfengda
 * @create 2018-08-13 22:06
 */
@Data
public class UserListVO {
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
    @JSONField(serializeUsing = TimeFormatMdSerializer.class)
    private Timestamp createTime;
}
