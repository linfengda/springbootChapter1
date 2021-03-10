package com.lfd.srv.demo.support.redis.bean;

import lombok.Data;

/**
 * 描述: 测试int类型
 *
 * @author linfengda
 * @create 2018-10-04 12:30
 */
@Data
public class IntType {
    private int target;

    public IntType() {
        this.target = 123456789;
    }
}
