package com.linfengda.sb.chapter1.common.api.parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.common.api.entity.BaseType;
import com.linfengda.sb.chapter1.common.api.entity.RequestParam;
import com.linfengda.sb.chapter1.common.api.util.HttpServletUtil;
import com.linfengda.sb.chapter1.common.entity.ErrorCode;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 描述: HTTP请求参数转换为DTO
 *
 * @author linfengda
 * @create 2019-12-17 18:15
 */
@Slf4j
public class MyControllerArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * Attribute属性名（解决POST请求HttpServletRequest流只能读取一次问题）
     */
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";
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
        RequestParam requestParam = getRequestParam(nativeWebRequest);
        // 转换请求参数为控制器方法参数，如果为DTO则直接转换
        if (BaseType.isBaseDataType(methodParameter.getParameterType().getName())) {
            return requestParam.getObject(methodParameter.getParameterName(), methodParameter.getParameterType());
        }else if (java.util.List.class.equals(methodParameter.getParameterType())) {
            Class<?> genericClz = null;
            Type genericParameterType = methodParameter.getGenericParameterType();
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (null != actualTypeArguments && actualTypeArguments.length > 0) {
                    genericClz = (Class<?>) actualTypeArguments[0];
                }
             }
            List<?> list = requestParam.getJSONArray(methodParameter.getParameterName()).toJavaList(genericClz);
            return list;
        }else {
            return requestParam.toJavaObject(methodParameter.getParameterType());
        }
    }

    private RequestParam getRequestParam(NativeWebRequest nativeWebRequest) throws Exception {
        RequestParam requestParam = new RequestParam();
        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            requestParam = HttpServletUtil.getRequestParam(servletRequest);
            servletRequest.setAttribute(JSON_REQUEST_BODY, requestParam.toJSONString());
        }else {
            JSONObject json = JSON.parseObject(jsonBody);
            for (Map.Entry<String, Object> value : json.entrySet()) {
                requestParam.put(value.getKey(), value.getValue());
            }
        }
        return requestParam;
    }
}
