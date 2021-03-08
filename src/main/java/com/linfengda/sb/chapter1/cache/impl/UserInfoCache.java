package com.linfengda.sb.chapter1.cache.impl;

import com.linfengda.sb.chapter1.cache.SystemCachePrefix;
import com.linfengda.sb.chapter1.cache.dto.UserInfo;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description 用户token-userInfo缓存
 * @author linfengda
 * @date 2020-09-21 16:56
 */
@Slf4j
@Component
public class UserInfoCache {
    private static final long userTokenExpired = 24*60*60*1000L;
    @Resource
    private GenericRedisTemplate genericRedisTemplate;
    @Resource
    private UserTokenCache userTokenCache;


    public void initCache() {
        log.warn("初始化用户信息缓存...");
    }

    public void clearCache() {
        genericRedisTemplate.delete(SystemCachePrefix.USER_INFO_CACHE);
        log.warn("清除用户信息缓存...");
    }

    public void put(String token, UserInfo userInfo){
        genericRedisTemplate.hashPut(SystemCachePrefix.USER_INFO_CACHE, token, userInfo);
    }

    public void remove(String token){
        genericRedisTemplate.hashDel(SystemCachePrefix.USER_INFO_CACHE, token);
    }

    public UserInfo getUserInfo(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        UserInfo userInfo = genericRedisTemplate.hashGet(SystemCachePrefix.USER_INFO_CACHE, token);
        return userInfo;
    }

    /**
     * 检查当前token有效性
     * @param token
     * @return
     */
    public UserInfo checkUserInfo(String token){
        UserInfo userInfo = this.getUserInfo(token);
        if(null == userInfo || isTokenExpired(userInfo, token)){
            throw new BusinessException("用户token已失效!");
        }
        String currentLoginToken = userTokenCache.getToken(userInfo.getUserId().toString());
        if(!token.equals(currentLoginToken)){
            //当前用户已经在其他设备登陆，token被挤出
            log.info("token被挤出，线上token：{}，被挤出token：{}", currentLoginToken, token);
            this.remove(token);
            throw new BusinessException("用户token被挤出!");
        }
        return userInfo;
    }

    /**
     * 检查用户token是否已经过期
     * @param userInfo
     * @return {@code true} 已过期  {@code false} 未过期
     */
    private boolean isTokenExpired(UserInfo userInfo, String token){
        Long nowMillis = System.currentTimeMillis();
        //空闲时间
        Long freeTime = nowMillis - userInfo.getCacheLastAccessTime();
        if(freeTime >= userTokenExpired){
            this.remove(token);
            return true;
        }
        //重新更新用户最近访问时间
        userInfo.setCacheLastAccessTime(nowMillis);
        this.put(token, userInfo);
        return false;
    }
}
