package com.lfd.soa.srv.demo.support.apivalidator.type;

import com.lfd.soa.srv.demo.support.apivalidator.annotation.ApiValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 描述: 当使用以下注解时，会校验Bean
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
