package com.linfengda.sb.support.redis.performance.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述: 获取jedis模板
 *
 * @author linfengda
 * @create 2019-02-19 10:01
 */
public class JedisTemplateHelper {
    private static RedisTemplate<String, Object> redisTemplate;
    public static RedisTemplate<String, Object> getTemplate() {
        if (null == redisTemplate) {
            init();
        }
        return redisTemplate;
    }

    public static void init() {

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("127.0.0.1");
        standaloneConfiguration.setPort(6379);
        standaloneConfiguration.setDatabase(1);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);

        // 获取集群机器配置
        /*RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        clusterConfig.setClusterNodes(getClusterNodes("47.106.79.8:7001,47.106.79.8:7002,47.106.79.8:7003,119.23.181.11:7004,119.23.181.11:7005,119.23.181.11:7006"));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxWaitMillis(-1);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();*/
        // 获取序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 获取操作模板
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
    }

    private static Set<RedisNode> getClusterNodes(String clusterNodes) {
        String[] hostAndPorts = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for (String node : hostAndPorts) {
            String[] hp = node.split(":");
            nodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
        return nodes;
    }

}
