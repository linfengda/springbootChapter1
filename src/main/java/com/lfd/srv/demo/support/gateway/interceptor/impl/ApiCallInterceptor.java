package com.lfd.srv.demo.support.gateway.interceptor.impl;

import com.lfd.common.util.JsonUtil;
import com.lfd.srv.demo.Constant;
import com.lfd.common.util.TimeUtil;
import com.lfd.srv.demo.support.gateway.entity.RequestSessionBO;
import com.lfd.srv.demo.support.gateway.session.RequestSession;
import com.lfd.srv.demo.support.gateway.session.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @description 请求拦截
 * @author linfengda
 * @date 2020-12-16 16:43
 */
@Slf4j
@Component
public class ApiCallInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Long beginTime = System.currentTimeMillis();
		String traceId = UUID.randomUUID().toString();
		MDC.put(Constant.TRACE_ID, traceId);
		RequestSessionBO requestSessionBO = RequestSessionBO.builder()
				.traceId(traceId)
				.url(request.getRequestURI())
				.method(request.getMethod())
				.requestTime(beginTime)
				.build();
		RequestSession.put(requestSessionBO);
		log.info("请求路径: {}, 请求方式: {}, 请求开始时间: {}，traceId: {}", requestSessionBO.getUrl(), requestSessionBO.getMethod(), TimeUtil.format(requestSessionBO.getRequestTime(), "yyyy-MM-dd HH:mm:ss"), requestSessionBO.getTraceId());
		return true;
    }

	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		RequestSessionBO requestSessionBO = RequestSession.get();
		Long endTime = System.currentTimeMillis();
		log.info("请求路径: {}, 请求方式: {}, 请求参数: {}, 请求人: {}, 请求结束时间: {}，请求耗时：{}ms，traceId: {}", requestSessionBO.getUrl(), requestSessionBO.getMethod(), JsonUtil.toJson(requestSessionBO.getRequestParams()), UserSession.getUserName(), TimeUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), endTime- requestSessionBO.getRequestTime(), requestSessionBO.getTraceId());
		RequestSession.remove();
		MDC.remove(Constant.TRACE_ID);
	}
}
