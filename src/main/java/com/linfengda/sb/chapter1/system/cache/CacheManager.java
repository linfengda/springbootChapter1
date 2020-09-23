package com.linfengda.sb.chapter1.system.cache;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import com.linfengda.sb.chapter1.common.util.SpringUtil;
import com.linfengda.sb.chapter1.system.cache.impl.UserTokenCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 缓存列表
 *
 * @author linfengda
 * @create 2019-05-21 10:30
 */
@AllArgsConstructor
@Getter
@Slf4j
public enum CacheManager {
    /**
     * 用户token-userId缓存
     */
    USER_TOKEN_CACHE("userTokenCache", "用户token-userId缓存", CachePrefix.USER_TOKEN_CACHE),
    /**
     * 用户token-userInfo缓存
     */
    USER_INFO_CACHE("userInfoCache", "用户token-userInfo缓存", CachePrefix.USER_INFO_CACHE),

    /**
     * 系统组织：部门
     */
    SYS_ORG_PRODUCTION_TEAM_CACHE("sysOrgProductionTeamCache", "系统组织：部门", CachePrefix.SYS_ORG_PRODUCTION_TEAM_CACHE),
    /**
     * 系统组织：团队
     */
    SYS_ORG_TEAM_CACHE("sysOrgTeamCache", "系统组织：项目", CachePrefix.SYS_ORG_TEAM_CACHE),
    /**
     * 系统组织：人员
     */
    SYS_ORG_USER_CACHE("sysOrgUserCache", "系统组织：人员", CachePrefix.SYS_ORG_USER_CACHE),

    /**
     * 部门集合
     */
    SYS_ORG_PRODUCTION_TEAM_SET_CACHE("sysOrgProductionTeamSetCache", "部门集合", CachePrefix.SYS_ORG_PRODUCTION_TEAM_SET_CACHE),
    /**
     * 团队集合
     */
    SYS_ORG_TEAM_SET_CACHE("sysOrgTeamSetCache", "项目集合", CachePrefix.SYS_ORG_TEAM_SET_CACHE),
    /**
     * 人员集合
     */
    SYS_ORG_USER_SET_CACHE("sysOrgUserSetCache", "人员集合", CachePrefix.SYS_ORG_USER_SET_CACHE),
    ;

    private String code;
    private String name;
    private String prefix;


    public static void init(){
        try {
            log.info("初始化应用程序缓存中......");
            for (CacheManager cache :values()) {
                switch (cache) {
                    case USER_TOKEN_CACHE:
                        UserTokenCache userTokenCache = SpringUtil.getBean(UserTokenCache.class);
                        userTokenCache.initCache();
                    default:
                        break;
                }
            }
            log.info("初始化应用程序缓存成功!");
        } catch (Exception e) {
            log.error("初始化应用程序缓存失败:", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR_CODE, "初始化应用程序缓存失败！");
        }
    }
}
