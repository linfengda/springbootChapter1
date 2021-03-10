package com.lfd.srv.demo.support.validator.constraints;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: hunterplan
 * @description 枚举类校验逻辑
 * @author linfengda
 * @date 2020-07-28 11:01
 */
@SuppressWarnings("all")
@Slf4j
public class EnumValidtor implements ConstraintValidator<EnumValid, Object> {
    /**
     * 枚举类
     */
    private Class<? extends Enum> cls;
    /**
     * 枚举类状态字段名称
     */
    private String validateFieldName;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        cls = constraintAnnotation.target();
        validateFieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null){
            return Boolean.TRUE;
        }
        try {
            //枚举类验证
            Object[] objs = cls.getEnumConstants();
            Method method = getReadMethod();
            for (Object obj : objs) {
                Object code = null;
                if (null == method) {
                    code = obj.toString();
                }else {
                    code = method.invoke(obj, null);
                }
                if (value.toString().equals(code.toString())) {
                    return Boolean.TRUE;
                }
            }
        }catch (BeansException e){
            log.warn("EnumValidtor#BeansException", e);
            return Boolean.FALSE;
        } catch (IllegalAccessException e) {
            log.warn("EnumValidtor#IllegalAccessException",e);
            return Boolean.FALSE;
        } catch (InvocationTargetException e) {
            log.warn("EnumValidtor#InvocationTargetException",e);
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }
    
    private Method getReadMethod() throws BeansException {
        if (StringUtils.isEmpty(validateFieldName)) {
            return null;
        }
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(cls, validateFieldName);
        if (null == propertyDescriptor) {
            return null;
        }
        return propertyDescriptor.getReadMethod();
    }
}
