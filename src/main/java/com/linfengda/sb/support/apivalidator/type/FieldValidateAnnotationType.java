package com.linfengda.sb.support.apivalidator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

/**
 * 描述: 校验Field的注解类型
 *
 * @author linfengda
 * @create 2019-12-23 17:29
 */
@Getter
@AllArgsConstructor
public enum FieldValidateAnnotationType {
    /**
     * NotNull
     */
    NOT_NULL(NotNull.class.getName()),
    /**
     * Null
     */
    NULL(Null.class.getName()),
    /**
     * NotBlank
     */
    NOT_BLANK(NotBlank.class.getName()),
    /**
     * NotEmpty
     */
    NOT_EMPTY(NotEmpty.class.getName()),
    /**
     * Size
     */
    SIZE(Size.class.getName()),
    /**
     * Max
     */
    MAX(Max.class.getName()),
    /**
     * Min
     */
    MIN(Min.class.getName()),
    ;

    private String type;

    public static boolean isValidateAnnotation(String type){
        for (FieldValidateAnnotationType value : values()) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
