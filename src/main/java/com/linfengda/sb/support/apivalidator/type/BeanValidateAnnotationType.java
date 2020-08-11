package com.linfengda.sb.support.apivalidator.type;

import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 描述: 校验Bean的注解类型，之所以自定义是为了跟{@link Validated}，{@link Valid}这些spring mvc本身支持的注解区分开来，如果一个api方法本身带有{@link Validated}，{@link Valid}注解，那么会走spring mvc的校验，只有显式声明了{@link ApiValidator}注解，才会走ApiValidator包下的校验。
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
