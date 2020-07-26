package com.linfengda.sb.support.redis.lettuce;

import io.lettuce.core.RedisURI;
import io.lettuce.core.codec.RedisCodec;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: Lettuce集群配置
 *
 * @author linfengda
 * @create 2019-02-18 22:25
 */
@Data
public class LettuceClusterConfig {
    private List<RedisURI> redisURIList;
    private RedisCodec<byte[], byte[]> redisCodec;

    public LettuceClusterConfig(String clusterNodes, RedisCodec<byte[], byte[]> redisCodec) {
        Assert.notNull(clusterNodes, "redis clusterNodes配置不能为空");
        Assert.notNull(redisCodec, "redis codec编码方式不能为空");
        this.redisURIList = getClusterURIList(clusterNodes);
        this.redisCodec = redisCodec;
    }

    /**
     * 获取集群机器配置
     * @return
     */
    private List<RedisURI> getClusterURIList(String clusterNodes) {
        List<RedisURI> redisURIList = new ArrayList<>();
        String[] nodes = clusterNodes.split(",");
        for (String node : nodes) {
            String[] ipPort = node.split(":");
            RedisURI redisURI = RedisURI.create(ipPort[0], Integer.valueOf(ipPort[1]));
            redisURIList.add(redisURI);
        }
        return redisURIList;
    }
}
