package com.linfengda.sb.support.redis.util;

import com.linfengda.sb.support.redis.Constant;

import java.util.Random;

/**
 * 描述: 缓存工具方法
 *
 * @author: linfengda
 * @date: 2020-07-13 10:22
 */
public class CacheUtil {

    /**
     * 叠加随机过期时间
     * @param timeOutMillis    原始过期时间
     * @return                 叠加后的过期时间
     */
    public static long getRandomTime(Long timeOutMillis) {
        if (null == timeOutMillis) {
            return Constant.DEFAULT_NO_EXPIRE_TIME;
        }
        if (Constant.DEFAULT_NO_EXPIRE_TIME.equals(timeOutMillis)) {
            return timeOutMillis;
        }
        Random random = new Random();
        int randomTime = random.nextInt((int) Constant.DEFAULT_PRV_CACHE_SNOW_SLIDE_RANDOM_TIME);
        return timeOutMillis + randomTime;
    }

    /**
     * 更新lru key过期时间
     * @return  当前时间戳
     */
    public static double getKeyLruScore() {
        return (double) System.currentTimeMillis();
    }

    /**
     * 解析lru key过期时间
     * @param score 过期时间
     * @return      过期时间
     */
    public static double parseKeyLruScore(double score) {
        return (long) score;
    }
}
