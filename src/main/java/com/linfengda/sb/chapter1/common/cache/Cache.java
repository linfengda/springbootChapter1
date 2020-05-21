package com.linfengda.sb.chapter1.common.cache;

/**
 * 描述: 缓存自动释放
 *
 * @author linfengda
 * @create 2019-05-21 10:30
 */
public interface Cache {

    /**
     * 初始化缓存
     */
    void initCache();

    /**
     * 清除缓存
     */
    void clearCache();
}
