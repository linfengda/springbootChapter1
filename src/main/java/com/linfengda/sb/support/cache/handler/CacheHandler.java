package com.linfengda.sb.support.cache.handler;

/**
 * 描述: 缓存委派模式接口
 *
 * @author linfengda
 * @create 2020-06-15 14:10
 */
public interface CacheHandler {
    /**
     * 缓存
     * @return  查询缓存结果/删除缓存结果/更新缓存结果
     * @throws Throwable
     */
    Object doCache() throws Throwable;
}
