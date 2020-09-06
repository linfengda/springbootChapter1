package com.linfengda.sb.support.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.redis.GenericRedisTemplate;
import com.linfengda.sb.support.redis.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述: 配置redis
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
@Configuration
public class RedisConfig extends AbstractCacheConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;


    /**
     * 配置jedisConnectionFactory
     * @return
     */
    private JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setDatabase(database);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);
        return jedisConnectionFactory;
    }

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
     * @return
     */
    @Bean
    public GenericRedisTemplate genericRedisTemplate() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();
        GenericRedisTemplate genericRedisTemplate = new GenericRedisTemplate();
        genericRedisTemplate.setConnectionFactory(jedisConnectionFactory());
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
