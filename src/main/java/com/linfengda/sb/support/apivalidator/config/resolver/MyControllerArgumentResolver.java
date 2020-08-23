package com.linfengda.sb.support.apivalidator.config.resolver;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.apivalidator.type.BaseType;
import com.linfengda.sb.support.apivalidator.type.NotValidateParameterType;
import com.linfengda.sb.support.apivalidator.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 解析api方法参数
 *
 * @author linfengda
 * @create 2019-12-17 18:15
 */
@Slf4j
public class MyControllerArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (NotValidateParameterType.isNotValidateParameterType(methodParameter.getParameterType().getName())) {
            throw new BusinessException("请将"+ methodParameter.getParameterType().getName() +"类型参数封装为DTO！");
        }
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        JSONObject requestParam = HttpServletUtil.getRequestParam(servletRequest);
        if (BaseType.isBaseType(methodParameter.getParameterType().getName())) {
            return requestParam.getObject(methodParameter.getParameterName(), methodParameter.getParameterType());
        }else {
            return requestParam.toJavaObject(methodParameter.getParameterType());
        }
    }
}
