package com.linfengda.sb.support.cache.handler.strategy;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-15 17:54
 */
@Slf4j
@Data
public abstract class AbstractCacheStrategy implements CacheStrategy {
    private SimpleRedisTemplate simpleRedisTemplate;

    /**
     * 采取LRU算法淘汰数据
     * @param param 查询参数
     */
    protected void deleteLRU(CacheParamDTO param) {
        int removeNum = 0;
        while(Constant.DEFAULT_LRU_REMOVE_NUM > removeNum) {
            if (0 == getSimpleRedisTemplate().opsForList().size(param.getLruKey())) {
                return;
            }
            String toDelKey = getSimpleRedisTemplate().listGet(param.getLruKey());
            getSimpleRedisTemplate().deleteObject(toDelKey);
            log.debug("当前缓存大小超过限制：{}，采取LRU算法淘汰数据，lurKey={}，toDelKey={}", param.getMaxSize(), param.getLruKey(), toDelKey);
            removeNum++;
        }
    }
}
