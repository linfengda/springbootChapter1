package com.linfengda.sb.chapter1.redis.performance.service;

import com.linfengda.sb.chapter1.redis.performance.helper.BytePackageHelper;
import com.linfengda.sb.chapter1.redis.performance.helper.JedisTemplateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 描述:
 *
 * @author linfengda
 * @create 2019-02-19 23:22
 */
@Slf4j
public class JedisRedisOperationServiceImpl implements RedisOperationService {
    private static RedisTemplate<String, Object> redisTemplate = JedisTemplateHelper.getTemplate();

    @Override
    public void simpleStringOperation(int count) throws Exception {
        long setTime = 0;
        long getTime = 0;
        long delTime = 0;
        for (int i = 0; i < count; i++) {
            long t0 = System.currentTimeMillis();
            redisTemplate.opsForValue().set("key", "value");
            long t1 = System.currentTimeMillis();
            redisTemplate.opsForValue().get("key");
            long t2 = System.currentTimeMillis();
            redisTemplate.delete("key");
            long t3 = System.currentTimeMillis();
            setTime += t1-t0;
            getTime += t2-t1;
            delTime += t3-t2;
        }
        log.info("------------------------------------------------<string command> set service average time={}ms", setTime/count);
        log.info("------------------------------------------------<string command> get service average time={}ms", getTime/count);
        log.info("------------------------------------------------<string command> del service average time={}ms", delTime/count);
    }

    @Override
    public void stringSetOperation() throws Exception {
        redisTemplate.opsForValue().set("key:" + Thread.currentThread().getId(), BytePackageHelper.getBytePackage(10000));
    }

    @Override
    public void stringSetGetOperation() throws Exception {
        redisTemplate.opsForValue().set("key:" + Thread.currentThread().getId(), "value");
        redisTemplate.opsForValue().get("key:" + Thread.currentThread().getId());
    }

    @Override
    public void simpleListOperation() throws Exception {

    }
}
