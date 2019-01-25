package com.linfengda.sb.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate4JS;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate4PS;
import com.linfengda.sb.support.middleware.redis.serializer.ProtoStuffSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 描述: Redis配置
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
@SpringBootConfiguration
public class RedisConfig {
    @Value("${spring.redis.serializer}")
    private String serializer;
    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.commandTimeout}")
    private int commandTimeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;


    /**
     * Jedis客户端
     * @return
     */
    //@Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        clusterConfig.setClusterNodes(getClusterNodes(clusterNodes));
        clusterConfig.setMaxRedirects(maxRedirects);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, jedisPoolConfig);
        return jedisConnectionFactory;
    }

    /**
     * lettuce客户端
     * @return
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setClusterNodes(getClusterNodes(clusterNodes));
        redisClusterConfiguration.setMaxRedirects(maxRedirects);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration);
        return lettuceConnectionFactory;
    }

    private Set<RedisNode> getClusterNodes(String clusterNodes) {
        String[] cNodes = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
        return nodes;
    }

    /**
     * 注入包含redis常见操作的模板
     * @return
     */
    @Bean
    public SimpleRedisTemplate simpleRedisTemplate(LettuceConnectionFactory connectionFactory) {
        Serializer serializer = Serializer.getType(this.serializer);
        Assert.notNull(serializer, "序列化方式不能为空！");

        SimpleRedisTemplate simpleRedisTemplate = getRedisTemplate(serializer);
        RedisSerializer redisSerializer = getRedisSerializer(serializer);
        simpleRedisTemplate.setConnectionFactory(connectionFactory);
        // 配置序列化和反序列化方式
        simpleRedisTemplate.setKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setValueSerializer(redisSerializer);
        simpleRedisTemplate.setHashValueSerializer(redisSerializer);
        simpleRedisTemplate.afterPropertiesSet();
        return simpleRedisTemplate;
    }

    private SimpleRedisTemplate getRedisTemplate(Serializer serializer) {
        switch (serializer) {
            case protoStuff:
                return new SimpleRedisTemplate4PS();
            case jackson:
                return new SimpleRedisTemplate4JS();
            default:
                return null;
        }
    }

    private RedisSerializer getRedisSerializer(Serializer serializer) {
        switch (serializer) {
            case protoStuff:
                ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();
                return protoStuffSerializer;
            case jackson:
                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(om);
                return jackson2JsonRedisSerializer;
            default:
                return null;
        }
    }

    /**
     * 序列化类型
     */
    public enum Serializer {
        protoStuff, jackson;
        public static Serializer getType(String serializer) {
            for (Serializer v : values()) {
                if (v.name().equals(serializer)) {
                    return v;
                }
            }
            return jackson;
        }
    }
}
