package com.linfengda.sb.chapter1.redis.lettuce;

import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import lombok.Data;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

/**
 * 描述: 执行redis命令
 *
 * @author linfengda
 * @create 2019-02-11 18:00
 */
@Data
public class LettuceTemplate<K, V> {
    private LettuceConnectionFactory connectionFactory;
    private RedisSerializer keySerializer = null;
    private RedisSerializer valueSerializer = null;
    private boolean debugAble;



    /**
     * 获取redis-server信息
     */
    public void info() {
        execute(commands -> commands.info());
    }

    /*******************************************   String操作   *********************************************/

    public void set(K key, V value) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value);
        execute(commands -> commands.set(rawKey, rawValue));
    }

    public V get(K key) {
        byte[] rawKey = rawKey(key);
        V result = execute(commands -> commands.get(rawKey));
        return deserializeValue((byte[]) result);
    }

    public Long del(K key) {
        byte[] rawKey = rawKey(key);
        return execute(commands -> commands.del(rawKey));
    }

    /*******************************************   String操作   *********************************************/

    /*******************************************   List操作   *********************************************/

    public Long leftPush(K key, V value) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value);
        return execute(commands -> commands.lpush(rawKey, rawValue));
    }

    public Long rightPush(K key, V value) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value);
        return execute(commands -> commands.rpush(rawKey, rawValue));
    }

    public V leftPop(K key) {
        byte[] rawKey = rawKey(key);
        return execute(commands -> commands.lpop(rawKey));
    }

    public V rightPop(K key) {
        byte[] rawKey = rawKey(key);
        return execute(commands -> commands.rpop(rawKey));
    }

    /*******************************************   List操作   *********************************************/


    /**
     * 所有redisCommand在execute方法执行，已方便资源管理和异常处理
     *
     * @param commandCallback
     * @return
     */
    public <V> V execute(LettuceCommandCallback commandCallback) {
        StatefulRedisClusterConnection<byte[], byte[]> connection = null;
        try {
            // 获取连接资源
            connection = getConnectionFactory().getConnection();
            RedisClusterCommands<byte[], byte[]> commands = connection.sync();
            // 执行redis命令
            V result = (V) commandCallback.call(commands);
            return result;



        } catch (Exception e) {
            if (isDebugAble()) {
                e.printStackTrace();
            }
            // 异常类型转换
            throw new LettuceConnectionException("redis命令执行异常：" + e.getStackTrace());
        } finally {
            // 释放连接资源
            if (null != connection) {
                //connectionFactory.releaseConnection();
            }
        }
    }

    byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (key instanceof byte[]) {
            return (byte[]) key;
        }
        return getKeySerializer().serialize(key);
    }

    byte[] rawValue(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        return getValueSerializer().serialize(value);
    }

    byte[][] rawValues(Object... values) {
        byte[][] rawValues = new byte[values.length][];
        int i = 0;
        for (Object value : values) {
            rawValues[i++] = rawValue(value);
        }
        return rawValues;
    }

    V deserializeValue(byte[] value) {
        return (V) getValueSerializer().deserialize(value);
    }
}
