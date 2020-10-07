package com.linfengda.sb.support.redis.entity.bo;

import lombok.Data;

/**
 * 描述: 测试double类型
 *
 * @author linfengda
 * @create 2018-10-04 12:29
 */
@Data
public class DoubleClazz {
    private double target;

    public DoubleClazz() {
        this.target = 123456789.123456789;
    }
}
