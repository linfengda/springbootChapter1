package com.linfengda.sb.chapter1.common.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linfengda.sb.chapter1.common.api.entity.HttpMethod;
import com.linfengda.sb.chapter1.common.api.entity.RequestInfoBO;
import com.linfengda.sb.chapter1.common.api.entity.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述: HTTP请求处理工具类
 *
 * @author linfengda
 * @create 2018-08-19 23:15
 */
@Slf4j
public class HttpServletUtil {
    /**
     * Attribute属性名（解决POST请求HttpServletRequest流只能读取一次问题）
     */
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    private static final String CHAR_CODE_SET = "UTF-8";

    private static final String INTERNATIONAL_CODE = "ISO8859-1";


    public static RequestInfoBO getRequestInfoBO() throws Exception {
        HttpServletRequest servletRequest = getHttpServletRequest();
        RequestInfoBO requestInfoBO = new RequestInfoBO();
        requestInfoBO.setIp(IpUtil.getIp(servletRequest));
        requestInfoBO.setUrl(servletRequest.getRequestURI());
        requestInfoBO.setRequestParam(getRequestParam(servletRequest));
        return requestInfoBO;
    }

    public static RequestParam getRequestParam(HttpServletRequest servletRequest) throws Exception {
        RequestParam requestParam = new RequestParam();
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (jsonBody == null) {
            requestParam = readRequestParam(servletRequest);
            servletRequest.setAttribute(JSON_REQUEST_BODY, requestParam.toJSONString());
        }else {
            JSONObject json = JSON.parseObject(jsonBody);
            if (json == null) {
                return requestParam;
            }
            for (Map.Entry<String, Object> value : json.entrySet()) {
                requestParam.put(value.getKey(), value.getValue());
            }
        }
        return requestParam;
    }

    /**
     * 获取http request
     *
     * @return
     */
    private static HttpServletRequest getHttpServletRequest() {
        RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
        if (reqAttrs != null && reqAttrs instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) reqAttrs).getRequest();
        } else {
            log.warn("无法获取HttpServletRequest");
            return null;
        }
    }

    /**
     * 获取http response
     *
     * @return
     */
    private static HttpServletResponse getHttpServletResponse() {
        RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
        if (reqAttrs != null && reqAttrs instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) reqAttrs).getResponse();
        } else {
            log.warn("无法获取HttpServletResponse");
            return null;
        }
    }

    private static RequestParam readRequestParam(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.getHttpMethod(method.toLowerCase());

        RequestParam requestParam = new RequestParam();
        switch (httpMethod) {
            case GET:
                Map<String, String[]> requestParams = request.getParameterMap();
                for (Map.Entry<String, String[]> value : requestParams.entrySet()) {
                    if (value.getValue().length == 1) {
                        String v = value.getValue()[0];
                        if (!StringUtils.isEmpty(v)) {
                            v = new String(v.getBytes(INTERNATIONAL_CODE), CHAR_CODE_SET);
                        }
                        requestParam.put(value.getKey(), v);
                    } else if (value.getValue().length > 1) {
                        String[] values = value.getValue();
                        for (int i = 0; i < values.length; i++) {
                            if (!StringUtils.isEmpty(values[i])) {
                                values[i] = new String(values[i].getBytes(INTERNATIONAL_CODE), CHAR_CODE_SET);
                            }
                        }
                        requestParam.put(value.getKey(), values);
                    }
                }
                break;
            case POST:
                String body = IOUtils.toString(request.getInputStream(), CHAR_CODE_SET);
                JSONObject json = JSON.parseObject(body);
                if (json == null) {
                    return requestParam;
                }
                for (Map.Entry<String, Object> value : json.entrySet()) {
                    requestParam.put(value.getKey(), value.getValue());
                }
                break;
        }
        return requestParam;
    }
}
