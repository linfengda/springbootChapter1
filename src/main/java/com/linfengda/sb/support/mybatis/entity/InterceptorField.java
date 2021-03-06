package com.linfengda.sb.support.mybatis.entity;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 拦截属性信息
 *
 * @author linfengda
 * @date 2021-03-05 16:28
 */
@Data
public class InterceptorField {
    /**
     * 字段
     */
    private Field field;
    /**
     * 字段拦截注解列表
     */
    private Map<String, Annotation> annotationMap;

    public boolean contains(String annotationName){
        return annotationMap.containsKey(annotationName);
    }

    public Annotation get(String annotationName){
        return annotationMap.get(annotationName);
    }
}
