package com.lfd.soa.srv.demo.cache.dto;

import lombok.Data;

/**
 * @description
 * @author linfengda
 * @date 2020-09-21 17:15
 */
@Data
public class UserInfo {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 缓存创建时间
     */
    private Long cacheCreateTime;
    /**
     * 缓存最近访问时间
     */
    private Long cacheLastAccessTime;
}
