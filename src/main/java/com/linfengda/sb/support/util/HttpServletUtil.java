package com.linfengda.sb.support.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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



    /**
     * 获取http request
     * @return  request
     */
    public static HttpServletRequest getHttpServletRequest() {
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
     * @return  response
     */
    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
        if (reqAttrs != null && reqAttrs instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) reqAttrs).getResponse();
        } else {
            log.warn("无法获取HttpServletResponse");
            return null;
        }
    }

    /**
     * 将请求参数统一封装为json格式
     * @param servletRequest    请求
     * @return                  json数据
     * @throws Exception
     */
    public static JSONObject getRequestParam(HttpServletRequest servletRequest) throws Exception {
        String jsonBody = (String) servletRequest.getAttribute(JSON_REQUEST_BODY);
        if (!StringUtils.isEmpty(jsonBody)) {
            JSONObject requestParam = JSON.parseObject(jsonBody);
            return requestParam;
        }
        JSONObject requestParam = readRequestParam(servletRequest);
        servletRequest.setAttribute(JSON_REQUEST_BODY, requestParam.toJSONString());
        return requestParam;
    }

    /**
     * 将请求参数统一封装为json格式
     * @param servletRequest    请求
     * @return                  json数据
     * @throws IOException
     */
    private static JSONObject readRequestParam(HttpServletRequest servletRequest) throws IOException {
        JSONObject requestParam = new JSONObject();
        String method = servletRequest.getMethod();
        switch (method) {
            case "GET":
                Map<String, String[]> requestParams = servletRequest.getParameterMap();
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
            case "POST":
                String body = IOUtils.toString(servletRequest.getInputStream(), CHAR_CODE_SET);
                requestParam = JSON.parseObject(body);
                break;
            default: return null;
        }
        return requestParam;
    }
}
