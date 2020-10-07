package com.linfengda.sb.chapter1.common.auth.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: IP地址工具类
 *
 * @author linfengda
 * @create 2019-12-24 10:57
 */
public class IpUtil {
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String UNKNOWN = "unknown";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String TTP_X_FORWARDED_FOR = "TTP_X_FORWARDED_FOR";

    /**
     * 获取当前请求IP地址
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader(PROXY_CLIENT_IP);
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader(WL_PROXY_CLIENT_IP);
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader(HTTP_CLIENT_IP);
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader(TTP_X_FORWARDED_FOR);
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getRemoteAddr();
        return ip;
    }

    private static boolean isValidIp(String ip) {
        if (!StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }
}
