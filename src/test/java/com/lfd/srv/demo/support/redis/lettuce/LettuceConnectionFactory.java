package com.lfd.srv.demo.support.redis.lettuce;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import lombok.Data;
import org.apache.ibatis.cache.CacheException;

/**
 * 描述: Lettuce连接管理
 * @author linfengda
 * @create 2019-02-11 16:26
 */
@Data
public class LettuceConnectionFactory {
    private LettuceClusterConfig clusterConfig;
    private RedisClusterClient redisClusterClient;


    public LettuceConnectionFactory(LettuceClusterConfig config) {
        clusterConfig = config;
        redisClusterClient = RedisClusterClient.create(clusterConfig.getRedisURIList());
    }

    public StatefulRedisClusterConnection<byte[], byte[]> getConnection() {
        StatefulRedisClusterConnection<byte[], byte[]> clusterConnection = redisClusterClient.connect(clusterConfig.getRedisCodec());
        return clusterConnection;
    }

    public void releaseConnection(StatefulRedisClusterConnection<byte[], byte[]> clusterConnection) {
        try{
            clusterConnection.close();
        }catch (Exception e) {
            throw new CacheException("连接关闭异常，" + e);
        }
    }
}
