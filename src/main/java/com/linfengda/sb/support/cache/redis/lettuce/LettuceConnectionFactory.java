package com.linfengda.sb.support.cache.redis.lettuce;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import lombok.Data;

/**
 * 描述: Lettuce连接管理
 *
 * @author linfengda
 * @create 2019-02-11 16:26
 */
@Data
public class LettuceConnectionFactory {
    private LettuceClusterConfig clusterConfig;
    private RedisClusterClient redisClusterClient;
    private StatefulRedisClusterConnection<byte[], byte[]> clusterConnection;


    public LettuceConnectionFactory(LettuceClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
        // 创建集Redis集群模式客户端
        redisClusterClient = RedisClusterClient.create(clusterConfig.getRedisURIList());
        // 连接到Redis集群
        clusterConnection = redisClusterClient.connect(clusterConfig.getRedisCodec());
    }

    public StatefulRedisClusterConnection<byte[], byte[]> getConnection() {
        if (!clusterConnection.isOpen()) {
            return redisClusterClient.connect(clusterConfig.getRedisCodec());
        }
        return clusterConnection;
    }

    public void releaseConnection() {
        clusterConnection.close();
        //redisClusterClient.shutdown();
    }

}
