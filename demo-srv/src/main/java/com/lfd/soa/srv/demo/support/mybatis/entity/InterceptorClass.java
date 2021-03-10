package com.lfd.soa.srv.demo.support.mybatis.entity;

import lombok.Data;

import java.util.List;

/**
 * 拦截类信息
 *
 * @author linfengda
 * @date 2021-03-05 16:28
 */
@Data
public class InterceptorClass {
    /**
     * 拦截类对象
     */
    private Class<?> clazz;
    /**
     * 拦截的字段列表
     */
    private List<InterceptorField> interceptorFieldList;
}
