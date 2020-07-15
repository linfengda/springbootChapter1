package com.linfengda.sb.support.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.cache.redis.lock.RedisDistributedLock;
import com.linfengda.sb.support.cache.redis.template.SimpleRedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述: Redis配置：默认使用Jackson2JsonRedisSerializer
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
    private JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setDatabase(database);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);
        return jedisConnectionFactory;
    }

    /**
     * 获取jackson序列化工具
     * @return
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public SimpleRedisTemplate simpleRedisTemplate(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        SimpleRedisTemplate simpleRedisTemplate = new SimpleRedisTemplate();
        simpleRedisTemplate.setConnectionFactory(getJedisConnectionFactory());
        simpleRedisTemplate.setKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        simpleRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return simpleRedisTemplate;
    }

    @Bean
    public RedisDistributedLock redisDistributedLock(SimpleRedisTemplate simpleRedisTemplate) {
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock();
        redisDistributedLock.setSimpleRedisTemplate(simpleRedisTemplate);
        return redisDistributedLock;
    }
}
