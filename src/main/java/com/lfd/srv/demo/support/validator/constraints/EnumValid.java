package com.lfd.srv.demo.support.validator.constraints;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * MES枚举正确性校验
 * MES校验工具类 {@link MesValidateUtils}
 * <p>校验参数传入的枚举值是否合法,用户传入系统不存在的枚举值时，将返回错误信息</p>
 * @author linfengda
 * @description
 * @date 2020-07-28 11:01
 */
@SuppressWarnings("all")
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidtor.class})
public @interface EnumValid {
    /**
     * 错误信息
     * 校验不通过将返回该信息
     */
    String message() default "invalid enum value";
    /**
     * 枚举类状态字段名称
     * e.g. {@link BasePO.Delete} code
     */
    String fieldName();
    /**
     * 枚举类
     */
    Class<? extends Enum> target();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
