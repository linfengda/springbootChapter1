package com.linfengda.sb.support.apivalidator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 描述: 校验Bean的注解类型
 *
 * @author linfengda
 * @create 2019-12-23 16:20
 */
@Getter
@AllArgsConstructor
public enum BeanValidateAnnotationType {
    /**
     * Valid
     */
    VALID(Valid.class.getName()),
    /**
     * Validator
     */
    VALIDATED(Validated.class.getName()),
    ;

    private String type;

    public static boolean isValidateAnnotation(String type){
        for (BeanValidateAnnotationType value : values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
