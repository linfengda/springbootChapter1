package com.linfengda.sb.support.middleware.redis.performance.helper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.redis.lettuce.LettuceClusterConfig;
import com.linfengda.sb.support.redis.lettuce.LettuceConnectionFactory;
import com.linfengda.sb.support.redis.lettuce.LettuceTemplate;
import io.lettuce.core.codec.ByteArrayCodec;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 描述: 获取lettuce模板，
 * @author linfengda
 * @create 2019-02-19 10:00
 */
public class LettuceTemplateHelper {

    public static LettuceTemplate<String, Object> getTemplate() {
        // 获取集群机器配置
        LettuceClusterConfig clusterConfig = new LettuceClusterConfig("47.106.79.8:7001,47.106.79.8:7002,47.106.79.8:7003,119.23.181.11:7004,119.23.181.11:7005,119.23.181.11:7006", ByteArrayCodec.INSTANCE);
        // 获取连接管理工厂
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(clusterConfig);
        // 获取序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        LettuceTemplate<String, Object> lettuceTemplate = new LettuceTemplate();
        lettuceTemplate.setConnectionFactory(connectionFactory);
        lettuceTemplate.setKeySerializer(new StringRedisSerializer());
        lettuceTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        lettuceTemplate.info();
        return lettuceTemplate;
    }

}
