package com.lfd.srv.demo.support.redis.service;

import com.lfd.srv.demo.support.redis.helper.BytePackageHelper;
import com.lfd.srv.demo.support.redis.helper.JedisTemplateHelper;
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
