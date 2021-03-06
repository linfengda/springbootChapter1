package com.linfengda.sb.support.mybatis.result.impl;

import cn.hutool.core.convert.Convert;
import com.linfengda.sb.support.mybatis.annotation.BizTimeField;
import com.linfengda.sb.support.mybatis.result.AbstractAnnotationResultInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 默认值时间字段拦截器
 *
 * @author linfengda
 * @date 2021-03-05 16:20
 */
public class BizTimeFieldInterceptor extends AbstractAnnotationResultInterceptor<BizTimeField> {

    public BizTimeFieldInterceptor(){
        super(BizTimeField.class);
    }

    @Override
    public Class<? extends Annotation> getInterceptorAnnotation() {
        return BizTimeField.class;
    }

    @Override
    public void interceptorField(Object target, Object value, Field field, BizTimeField bizTimeField) {
        if(!(value instanceof Date)){
            return;
        }
        Date defaultDate = Convert.toDate(bizTimeField.defaultTime());
        setFieldValue(field, target, defaultDate.equals(value) ? null : value);
    }
}
