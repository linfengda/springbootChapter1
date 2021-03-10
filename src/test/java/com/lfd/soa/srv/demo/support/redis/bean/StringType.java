package com.lfd.soa.srv.demo.support.redis.bean;

import lombok.Data;

/**
 * 描述: 测试String类型
 *
 * @author linfengda
 * @create 2018-10-06 22:25
 */
@Data
public class StringType {
    private String target;

    public StringType() {
        this.target = "123456789.123456789";
    }
}
