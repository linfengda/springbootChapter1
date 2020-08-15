package com.linfengda.sb.support.apivalidator.type;

import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 描述: 校验Bean的注解类型，有下列4中情况:
 * <li>1. 没有任何校验注解，不会做任何校验</li>
 * <li>2. 有@RequestBody注解和@ApiValidator校验注解，做ApiValidator的校验</li>
 * <li>3. 没有@RequestBody注解，有@ApiValidator/@Valid/@Validated校验注解，做ApiValidator的校验</li>
 *
 * @author linfengda
 * @create 2019-12-23 16:20
 */
@Getter
@AllArgsConstructor
public enum BeanValidateAnnotationType {
    /**
     * ApiValidator
     */
    API_VALIDATOR(ApiValidator.class.getName()),
    /**
     * Valid
     */
    VALID(Valid.class.getName()),
    /**
     * Validated
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
