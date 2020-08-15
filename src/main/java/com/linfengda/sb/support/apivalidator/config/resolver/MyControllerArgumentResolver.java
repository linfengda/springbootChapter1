package com.linfengda.sb.support.apivalidator.config.resolver;

import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.support.apivalidator.type.BaseType;
import com.linfengda.sb.support.apivalidator.util.HttpServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

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
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        JSONObject requestParam = HttpServletUtil.getRequestParam(servletRequest);
        if (BaseType.isBaseType(methodParameter.getParameterType().getName())) {
            return requestParam.getObject(methodParameter.getParameterName(), methodParameter.getParameterType());
        }else if (java.util.List.class.equals(methodParameter.getParameterType())) {
            return getList(methodParameter, requestParam);
        }else {
            return requestParam.toJavaObject(methodParameter.getParameterType());
        }
    }

    /**
     * 从请求参数中解析List
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
