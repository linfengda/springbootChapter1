package com.linfengda.sb.chapter1.common.api.parameter;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.common.api.entity.BaseType;
import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.chapter1.common.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * 描述: HTTP请求参数转换为DTO
 *
 * @author linfengda
 * @create 2019-12-17 18:15
 */
@Slf4j
public class MyControllerArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * API参数列表大小限制
     */
    private static final int MAX_CONTROLLER_PARAMETER_SIZE = 2;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        if (MAX_CONTROLLER_PARAMETER_SIZE < methodParameter.getParameterIndex()+1) {
            throw new BusinessException(ErrorCode.COMMON_PARAM_ERROR_CODE, "API参数大小限制为" +MAX_CONTROLLER_PARAMETER_SIZE+ "个");
        }
        // 转换请求参数为控制器方法参数
        RequestInfoBO requestInfoBO = HttpServletUtil.getRequestInfoBO();
        if (BaseType.isBaseType(methodParameter.getParameterType().getName())) {
            return requestInfoBO.getRequestParam().getObject(methodParameter.getParameterName(), methodParameter.getParameterType());
        }else if (java.util.List.class.equals(methodParameter.getParameterType())) {
            return getList(methodParameter, requestInfoBO.getRequestParam());
        }else {
            return requestInfoBO.getRequestParam().toJavaObject(methodParameter.getParameterType());
        }
    }

    /**
     * json参数转List
     * @param methodParameter   方法声明的参数信息
     * @param requestParam      请求参数
     * @return                  List
     */
    private Object getList(MethodParameter methodParameter, JSONObject requestParam) {
        if (null == requestParam.get(methodParameter.getParameterName())) {
            return null;
        }
        Class<?> genericClz = Object.class;
        Type genericParameterType = methodParameter.getGenericParameterType();
        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (null != actualTypeArguments && actualTypeArguments.length > 0) {
                if (actualTypeArguments[0] instanceof WildcardType) {
                    genericClz = Object.class;
                }else {
                    genericClz = (Class<?>) actualTypeArguments[0];
                }
            }
        }
        List<?> list = requestParam.getJSONArray(methodParameter.getParameterName()).toJavaList(genericClz);
        return list;
    }
}
