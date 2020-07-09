package com.linfengda.sb.chapter1.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.middleware.redis.lock.RedisDistributedLock;
import com.linfengda.sb.support.middleware.redis.template.JacksonRedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述: Redis配置
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
@SpringBootConfiguration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;


    /**
     * Jedis客户端
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setDatabase(database);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    public JacksonRedisTemplate jacksonRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        JacksonRedisTemplate jacksonRedisTemplate = new JacksonRedisTemplate();
        jacksonRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        jacksonRedisTemplate.setKeySerializer(new StringRedisSerializer());
        jacksonRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        jacksonRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        jacksonRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return jacksonRedisTemplate;
    }

    @Bean
    public RedisDistributedLock redisDistributedLock(JacksonRedisTemplate jacksonRedisTemplate) {
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock();
        redisDistributedLock.setJacksonRedisTemplate(jacksonRedisTemplate);
        return redisDistributedLock;
    }
}
