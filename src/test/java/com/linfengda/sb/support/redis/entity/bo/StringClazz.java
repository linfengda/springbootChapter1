package com.linfengda.sb.support.redis.entity.bo;

import lombok.Data;

/**
 * 描述: 测试String类型
 *
 * @author linfengda
 * @create 2018-10-06 22:25
 */
@Data
public class StringClazz {
    private String target;

    public StringClazz() {
        this.target = "123456789.123456789";
    }
}
