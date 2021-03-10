package com.lfd.soa.srv.demo.support.redis.bean;

import lombok.Data;

/**
 * 描述: 测试double类型
 *
 * @author linfengda
 * @create 2018-10-04 12:29
 */
@Data
public class DoubleType {
    private double target;

    public DoubleType() {
        this.target = 123456789.123456789;
    }
}
