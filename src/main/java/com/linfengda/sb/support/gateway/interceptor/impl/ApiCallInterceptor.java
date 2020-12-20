package com.linfengda.sb.support.gateway.interceptor.impl;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.chapter1.Constant;
import com.linfengda.sb.chapter1.common.util.TimeUtil;
import com.linfengda.sb.support.gateway.session.RequestInfoBO;
import com.linfengda.sb.support.gateway.session.RequestSessionHelper;
import com.linfengda.sb.support.gateway.session.UserSessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @description: 请求拦截
 * @author: linfengda
 * @date: 2020-12-16 16:43
 */
@Slf4j
@Component
public class ApiCallInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Long beginTime = System.currentTimeMillis();
		String traceId = UUID.randomUUID().toString();
		MDC.put(Constant.TRACE_ID, traceId);
		RequestInfoBO requestInfoBO = RequestInfoBO.builder()
				.traceId(traceId)
				.url(request.getRequestURI())
				.method(request.getMethod())
				.requestParams("")
				.request(request)
				.requestTime(beginTime)
				.build();
		RequestSessionHelper.put(requestInfoBO);
		log.info("请求路径: {}, 请求方式: {}, 请求参数: {}, 请求开始时间: {}，traceId: {}", requestInfoBO.getUrl(), requestInfoBO.getMethod(), JSON.toJSONString(requestInfoBO.getRequestParams()), TimeUtil.format(requestInfoBO.getRequestTime(), "yyyy-MM-dd HH:mm:ss"), requestInfoBO.getTraceId());
		return true;
    }

	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		RequestInfoBO requestInfoBO = RequestSessionHelper.get();
		Long endTime = System.currentTimeMillis();
		log.info("请求路径: {}, 请求方式: {}, 请求人: {}, 请求结束时间: {}，请求耗时：{}ms，traceId: {}", requestInfoBO.getUrl(), requestInfoBO.getMethod(), UserSessionHelper.getUserName(), TimeUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), endTime- requestInfoBO.getRequestTime(), requestInfoBO.getTraceId());
		RequestSessionHelper.remove();
		MDC.remove(Constant.TRACE_ID);
    }
}
