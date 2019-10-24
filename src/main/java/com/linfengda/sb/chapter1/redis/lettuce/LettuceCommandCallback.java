package com.linfengda.sb.chapter1.redis.lettuce;

import io.lettuce.core.cluster.api.sync.RedisClusterCommands;


@FunctionalInterface
public interface LettuceCommandCallback<T> {

    T call(RedisClusterCommands commands) throws Exception;
}
