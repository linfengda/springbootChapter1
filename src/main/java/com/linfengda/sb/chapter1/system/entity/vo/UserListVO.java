package com.linfengda.sb.chapter1.system.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.linfengda.sb.chapter1.common.api.serializer.LongToStringSerializer;
import lombok.Data;

/**
 * 描述: 电影简介显示信息
 *
 * @author linfengda
 * @create 2018-08-13 22:06
 */
@Data
public class UserListVO {
    /**
     * 用户ID
     */
    @JSONField(serializeUsing = LongToStringSerializer.class)
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
}
