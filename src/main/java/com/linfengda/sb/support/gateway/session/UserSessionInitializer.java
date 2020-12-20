package com.linfengda.sb.support.gateway.session;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * @description: 用户session初始化器
 * @author: linfengda
 * @date: 2020-12-15 22:33
 */
@Slf4j
@Component
public class UserSessionInitializer implements UserSessionConstant {


    /**
     * 根据token从统一登陆平台获取
     * @param authToken token
     * @return          用户session信息
     */
    public UserSessionBO initFromPlatform(String authToken) {
        UserSessionBO userSessionBO = UserSessionBO.builder()
                .userId("123")
                .userName("林丰达")
                .authorization(authToken)
                .build();
        log.info("初始化用户会话信息：{}", JSON.toJSONString(userSessionBO));
        return userSessionBO;
    }

    /**
     * 从请求Header中获取（非统一登陆平台系统服务调用）
     * @param request           请求
     * @return                  用户session信息
     */
    public UserSessionBO initFromHeader(HttpServletRequest request) {
        UserSessionBO userSessionBO = UserSessionBO.builder()
                .userId(getHeader(request, USER_ID))
                .userName(getHeader(request, USER_NAME))
                .authorization(getHeader(request, AUTHORIZATION))
                .build();
        log.info("初始化用户会话信息：{}", JSON.toJSONString(userSessionBO));
        return userSessionBO;
    }

    private String getHeader(HttpServletRequest request, String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            String value = request.getHeader(key);
            if (null == value) {
                return null;
            }
            return URLDecoder.decode(Optional.ofNullable(value).orElse(""), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            log.error("decode编码{}失败！", key, e);
        }
        return null;
    }
}
