package com.linfengda.sb.chapter1.serializeTest.entity.vo;

import lombok.Data;

/**
 * 描述: 测试int类型
 *
 * @author linfengda
 * @create 2018-10-04 12:30
 */
@Data
public class IntClazz {
    private int target;

    public IntClazz() {
        this.target = 123456789;
    }
}
