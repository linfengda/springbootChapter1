package com.lfd.soa.srv.demo.support.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfd.soa.srv.demo.support.redis.GenericRedisTemplate;
import com.lfd.soa.srv.demo.support.redis.lock.RedisDistributedLock;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述: 配置redis
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
public class RedisConfig {

    /**
     * 配置Jackson2JsonRedisSerializer
     * @return
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 配置默认GenericRedisTemplate
     * @param redisConnectionFactory    默认LettuceConnectionFactory
     * @return
     */
    @Bean
    public GenericRedisTemplate genericRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();
        GenericRedisTemplate genericRedisTemplate = new GenericRedisTemplate();
        genericRedisTemplate.setConnectionFactory(redisConnectionFactory);
        genericRedisTemplate.setKeySerializer(new StringRedisSerializer());
        genericRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        genericRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        genericRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return genericRedisTemplate;
    }

    /**
     * 配置默认RedisDistributedLock
     * @return
     */
    @Bean
    public RedisDistributedLock redisDistributedLock(GenericRedisTemplate genericRedisTemplate) {
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock();
        redisDistributedLock.setRedisTemplate(genericRedisTemplate);
        return redisDistributedLock;
    }
}
