package com.linfengda.sb.support.validator;

import com.linfengda.sb.support.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @program: hunterplan
 * @description: MES对象参数校验工具类,遵循JSR303校验
 * @author: linfengda
 * @date: 2020-07-25 18:01
 */
public class MyValidateUtils {

    private MyValidateUtils(){}

    private static Validator validator;

    static {
        //使用Hibernate校验
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * 参数校验方法
     * @param t 需要校验的对象
     * @param <T>
     */
    public static <T> void validate(T t) {
        if (t == null){
            throw new BusinessException("参数异常");
        }
        Set<ConstraintViolation<T>> result = validator.validate(t);
        if(CollectionUtils.isNotEmpty(result)){
            ConstraintViolation<T> firstViolation = result.stream().findFirst().get();
            String firstErrorMsg = firstViolation.getMessage();
            throw new BusinessException(firstErrorMsg);
        }
    }

    /**
     * 参数校验方法
     * @param t 需要校验的对象
     * @param <T>
     */
    public static <T> void validate(T t, Class<?>... groupClazz) {
        if (t == null){
            throw new BusinessException("参数异常");
        }
        Set<ConstraintViolation<T>> result = validator.validate(t,groupClazz);
        if(CollectionUtils.isNotEmpty(result)){
            ConstraintViolation<T> firstViolation = result.stream().findFirst().get();
            String firstErrorMsg = firstViolation.getMessage();
            throw new BusinessException(firstErrorMsg);
        }
    }

    /**
     * 参数校验方法
     * @param object    需要校验的对象
     * @param method    需要校验的对象方法
     * @param parameterValues   校验的对象方法参数
     * @param groups    分组
     * @param <T>
     */
    public static <T> void validateParameters(T object, Method method, Object[] parameterValues, Class<?>... groups) {
        ExecutableValidator execVal = validator.forExecutables();
        Set<ConstraintViolation<T>> result = execVal.validateParameters(object, method, parameterValues, groups);
        if(CollectionUtils.isNotEmpty(result)){
            ConstraintViolation<T> firstViolation = result.stream().findFirst().get();
            String firstErrorMsg = firstViolation.getMessage();
            throw new BusinessException(firstErrorMsg);
        }
    }
}
