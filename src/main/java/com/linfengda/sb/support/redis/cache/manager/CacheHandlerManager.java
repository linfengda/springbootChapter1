package com.linfengda.sb.support.redis.cache.manager;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheDataDTO;
import com.linfengda.sb.support.redis.cache.entity.type.OperationType;
import com.linfengda.sb.support.redis.cache.exception.CahcheException;
import com.linfengda.sb.support.redis.cache.handler.CacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.DeleteCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.QueryCacheHandler;
import com.linfengda.sb.support.redis.cache.handler.impl.UpdateCacheHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 缓存操作类型
 * @author: linfengda
 * @date: 2020-06-16 16:18
 */
@Getter
@AllArgsConstructor
public enum CacheHandlerManager {
    /**
     * 查询
     */
    EN_PASS(OperationType.QUERY, "查询") {
        @Override
        public CacheHandler getHandler(CacheDataDTO cacheDataDTO) {
            return new QueryCacheHandler(cacheDataDTO);
        }
    },
    /**
     * 删除
     */
    EX_PASS(OperationType.DELETE, "删除") {
        @Override
        public CacheHandler getHandler(CacheDataDTO cacheDataDTO) {
            return new DeleteCacheHandler(cacheDataDTO);
        }
    },
    /**
     * 更新
     */
    GANTRY_PASS(OperationType.UPDATE, "更新") {
        @Override
        public CacheHandler getHandler(CacheDataDTO cacheDataDTO) {
            return new UpdateCacheHandler(cacheDataDTO);
        }
    },
    ;


    /**
     * 缓存操作类型
     */
    private OperationType type;
    /**
     * 描述
     */
    private String desc;

    public abstract CacheHandler getHandler(CacheDataDTO cacheDataDTO);

    /**
     * 获取缓存处理器
     * @param cacheDataDTO 缓存数据DTO
     */
    public static CacheHandler provide(CacheDataDTO cacheDataDTO) {
        for (CacheHandlerManager value : values()) {
            if (value.getType() == cacheDataDTO.getType()) {
                return value.getHandler(cacheDataDTO);
            }
        }
        throw new CahcheException("不支持的缓存操作！");
    }
}
