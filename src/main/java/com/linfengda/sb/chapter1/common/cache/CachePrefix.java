package com.linfengda.sb.chapter1.common.cache;

/**
 * @description: 缓存命名空间
 * @author: linfengda
 * @date: 2020-07-27 23:54
 */
public interface CachePrefix {
    /**
     * 用户token-userId缓存
     */
    String USER_TOKEN_CACHE = "sys:org:t:u:c";
    /**
     * 用户token-userInfo缓存
     */
    String USER_INFO_CACHE = "sys:org:u:i:c";
    /**
     * 系统组织：部门
     */
    String SYS_ORG_PRODUCTION_TEAM_CACHE = "sys:org:pt";
    /**
     * 系统组织：团队
     */
    String SYS_ORG_TEAM_CACHE = "sys:org:tm";
    /**
     * 系统组织：人员
     */
    String SYS_ORG_USER_CACHE = "sys:org:usr";
    /**
     * 部门集合
     */
    String SYS_ORG_PRODUCTION_TEAM_SET_CACHE = "sys:org:pt:set";
    /**
     * 团队集合
     */
    String SYS_ORG_TEAM_SET_CACHE = "sys:org:tm:set";
    /**
     * 人员集合
     */
    String SYS_ORG_USER_SET_CACHE = "sys:org:usr:set";
}
