package com.linfengda.sb.support.redis.cache.manager;

import com.linfengda.sb.chapter1.common.exception.BusinessException;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
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
    EN_PASS(CacheAnnotationType.QUERY, "查询") {
        @Override
        public CacheHandler getHandler(CacheTargetDTO cacheTargetDTO) {
            return new QueryCacheHandler(cacheTargetDTO);
        }
    },
    /**
     * 删除
     */
    EX_PASS(CacheAnnotationType.DELETE, "删除") {
        @Override
        public CacheHandler getHandler(CacheTargetDTO cacheTargetDTO) {
            return new DeleteCacheHandler(cacheTargetDTO);
        }
    },
    /**
     * 更新
     */
    GANTRY_PASS(CacheAnnotationType.UPDATE, "更新") {
        @Override
        public CacheHandler getHandler(CacheTargetDTO cacheTargetDTO) {
            return new UpdateCacheHandler(cacheTargetDTO);
        }
    },
    ;


    /**
     * 缓存操作类型
     */
    private CacheAnnotationType annotationType;
    /**
     * 描述
     */
    private String desc;

    public abstract CacheHandler getHandler(CacheTargetDTO cacheTargetDTO);

    /**
     * 获取缓存处理器
     * @param cacheTargetDTO    缓存数据DTO
     * @param annotationType    缓存操作类型
     * @return                  handler
     */
    public static CacheHandler provide(CacheTargetDTO cacheTargetDTO, CacheAnnotationType annotationType) {
        for (CacheHandlerManager value : values()) {
            if (value.getAnnotationType() == annotationType) {
                return value.getHandler(cacheTargetDTO);
            }
        }
        throw new BusinessException("不支持的缓存操作！" + cacheTargetDTO.toString());
    }
}
