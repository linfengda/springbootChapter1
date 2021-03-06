package com.linfengda.sb.support.gateway.session;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.linfengda.sb.support.gateway.entity.UserSessionBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


/**
 * @description 访问api接口用户缓存，通过com.linfengda.sb.support.gateway.interceptor.impl.AuthInterceptor拦截器后初始化
 * @author linfengda
 * @date 2020-12-20 20:12
 */
@Slf4j
public class UserSessionHelper {
    private final static ThreadLocal<UserSessionBO> USER_SESSION_INFO_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void put(UserSessionBO session){
        USER_SESSION_INFO_THREAD_LOCAL.set(session);
    }

    public static void remove() {
        USER_SESSION_INFO_THREAD_LOCAL.remove();
    }

    /**
     * 获取当前请求用户的userId
     * @return
     */
    public static String getUserId() {
        UserSessionBO userSessionBO = USER_SESSION_INFO_THREAD_LOCAL.get();
        if (null == userSessionBO) {
            return "";
        }
        String userId = userSessionBO.getUserId();
        if (StringUtils.isEmpty(userId)) {
            return "";
        }
        return userId;
    }

    /**
     * 获取当前请求用户的名称
     * @return
     */
    public static String getUserName() {
        UserSessionBO userSessionBO = USER_SESSION_INFO_THREAD_LOCAL.get();
        if (null == userSessionBO) {
            return "system";
        }
        String userName = userSessionBO.getUserName();
        if (userName == null) {
            return "system";
        }
        return userName;
    }

    /**
     * 获取当前请求的Authorization字符
     * @return
     */
    public static String getAuthorization() {
        UserSessionBO userSessionBO = USER_SESSION_INFO_THREAD_LOCAL.get();
        if (null == userSessionBO) {
            return "";
        }
        String authorization = userSessionBO.getAuthorization();
        if (authorization == null) {
            return "";
        }
        return authorization;
    }
}
