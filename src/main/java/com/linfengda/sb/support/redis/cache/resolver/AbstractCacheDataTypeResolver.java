package com.linfengda.sb.support.redis.cache.resolver;

import com.alibaba.fastjson.JSON;
import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.cache.annotation.QueryCache;
import com.linfengda.sb.support.redis.cache.entity.bo.CacheResultBO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.HashKey;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import com.linfengda.sb.support.redis.cache.entity.type.CacheMaxSizeStrategy;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Set;

/**
 * 描述:
 *
 * @author: linfengda
 * @date: 2020-07-15 17:54
 */
@Slf4j
public abstract class AbstractCacheDataTypeResolver implements CacheDataTypeResolver {
    @Setter
    protected GenericRedisTemplate genericRedisTemplate;


    @Override
    public CacheResultBO getCache(CacheParamDTO param) {
        CacheResultBO resultBO = doGetCache(param);
        if (resultBO.getHasKey() && CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
            // 更新lru缓存过期时间
            setCache(param, resultBO.getTarget());
            // 更新lru缓存标识
            genericRedisTemplate.opsForZSet().add(param.getLruKey(), param.getKey(), param.getLruKeyScore());
        }
        return resultBO;
    }

    @Override
    public void setCache(CacheParamDTO param, Object value) {
        if (CacheAnnotationType.UPDATE == param.getCacheAnnotationType() || CacheAnnotationType.DELETE == param.getCacheAnnotationType()) {
            doSetCache(param, value);
            return;
        }
        if (Constant.DEFAULT_NO_SIZE_LIMIT == param.getMaxSize()) {
            // 未设置最大缓存数量
            doSetCache(param, value);
        }else {
            // 设置最大缓存数量
            boolean hasSize = hasSize(param);
            if (hasSize) {
                // 设置最大缓存数量但有空间
                doSetCache(param, value);
                if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
                    genericRedisTemplate.opsForZSet().add(param.getLruKey(), param.getKey(), param.getLruKeyScore());
                }
            }else {
                // 设置最大缓存数量且没有空间
                if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON == param.getMaxSizeStrategy()) {
                    log.debug("当前缓存：{}超过最大缓存限制：{}，缓存策略：CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_ABANDON，将不再缓存数据！", param.getPrefix(), param.getMaxSize());
                }else if (CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU == param.getMaxSizeStrategy()) {
                    log.debug("当前缓存：{}超过最大缓存限制：{}，缓存策略：CacheMaxSizeStrategy.MAX_SIZE_STRATEGY_LRU，将淘汰数据！", param.getPrefix(), param.getMaxSize());
                    deleteLRU(param);
                    doSetCache(param, value);
                    genericRedisTemplate.opsForZSet().add(param.getLruKey(), param.getKey(), param.getLruKeyScore());
                }
            }
        }
    }

    /**
     * 采取LRU算法批量淘汰数据 {@link QueryCache#deleteLruBatchNum()}
     * @param param 查询参数
     */
    private void deleteLRU(CacheParamDTO param) {
        int num = param.getDeleteLruBatchNum()-1;
        Long size = genericRedisTemplate.opsForZSet().size(param.getLruKey());
        if (null == size || 0 == size) {
            return;
        }
        Set<Object> delKeys = genericRedisTemplate.opsForZSet().range(param.getLruKey(), 0, num);
        // 删除LRU记录
        genericRedisTemplate.opsForZSet().removeRange(param.getLruKey(), 0, num);
        // 删除具体缓存key
        for (Object delKey : delKeys) {
            if (null == delKey) {
                continue;
            }
            CacheParamDTO delParam = new CacheParamDTO();
            delParam.setAllEntries(false);
            String key = String.valueOf(delKey);
            if (DataType.HASH == param.getDataType()) {
                HashKey hashKey = new HashKey();
                hashKey.setKey(key.substring(0, key.lastIndexOf(Constant.COLON)));
                hashKey.setHashKey(key.substring(key.lastIndexOf(Constant.COLON)+1));
                delParam.setHashKey(hashKey);
            }else {
                delParam.setKey(key);
            }
            delCache(delParam);
        }
        log.debug("当前缓存：{}超过最大缓存限制：{}，采取LRU算法淘汰{}条数据，lurKey={}，delKeys={}", param.getPrefix(), param.getMaxSize(), Constant.DEFAULT_LRU_CACHE_BG_REMOVE_BATCH_NUM, param.getLruKey(), JSON.toJSONString(delKeys));
    }

    protected void delAllEntries(CacheParamDTO param) {
        // 删除key=prefix的缓存
        genericRedisTemplate.delete(param.getPrefix());
        // 删除prefix*开头的所有缓存
        genericRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(param.getPrefix() + Constant.ASTERISK).count(Constant.DEFAULT_DELETE_CACHE_BATCH_NUM).build());
            while(cursor.hasNext()) {
                String key = new String(cursor.next());
                genericRedisTemplate.delete(key);
            }
            return true;
        });
    }

    /**
     * 查询缓存
     * @param param 缓存参数
     * @return      缓存数据
     */
    public abstract CacheResultBO doGetCache(CacheParamDTO param);

    /**
     * 写入缓存
     * @param param 缓存参数
     * @param value 缓存数据
     */
    public abstract void doSetCache(CacheParamDTO param, Object value);
}
