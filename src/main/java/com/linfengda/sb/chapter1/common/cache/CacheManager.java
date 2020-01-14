package com.linfengda.sb.chapter1.common.cache;

import com.linfengda.sb.chapter1.common.context.ApplicationContextHelper;
import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.chapter1.common.exception.entity.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 缓存列表
 *
 * @author linfengda
 * @create 2019-05-21 10:30
 */
@Slf4j
public enum CacheManager {
    /**
     * 组织关系缓存
     */
    ORGANIZE_CACHE("organizeCache", "组织关系缓存", "c:org"),
    ;

    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private String prefix;

    CacheManager(String code, String name, String prefix) {
        this.code = code;
        this.name = name;
        this.prefix = prefix;
    }

    public static void init(){
        try {
            log.info("初始化应用程序缓存中......");
            for (CacheManager cache :values()) {
                switch (cache) {
                    case ORGANIZE_CACHE:
                        OrganizeCache organizeCache = ApplicationContextHelper.getBean(OrganizeCache.class);
                        organizeCache.clearCache();
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
