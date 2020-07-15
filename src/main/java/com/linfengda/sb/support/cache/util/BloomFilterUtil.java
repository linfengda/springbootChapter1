package com.linfengda.sb.support.cache.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.linfengda.sb.support.cache.config.RedisSupportHolder;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * 描述: 布隆过滤器
 *
 * @author: linfengda
 * @date: 2020-07-13 15:36
 */
public class BloomFilterUtil {
    private BloomFilterUtil(){}

    private static BloomFilter<byte[]> bloomFilter;
    static {
        bloomFilter = BloomFilter.create((Funnel<byte[]>) (from, into) -> into.putBytes(from), 1000000, 0.01);
    }

    /**
     * 向布隆过滤器添加元素
     */
    public static void put(String key, Object value) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = RedisSupportHolder.getJackson2JsonRedisSerializer();
        byte[] bytes = jackson2JsonRedisSerializer.serialize(value);
        bloomFilter.put(bytes);
    }

    /**
     * 使用布隆过滤器过滤元素
     */
    public static boolean mightContain(String key, Object value) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = RedisSupportHolder.getJackson2JsonRedisSerializer();
        byte[] bytes = jackson2JsonRedisSerializer.serialize(value);
        return bloomFilter.mightContain(bytes);
    }
}
