package com.lfd.soa.srv.demo.support.redis.lettuce;

import io.lettuce.core.cluster.api.sync.RedisClusterCommands;


/**
 * 描述: 函数式编程接口，方便调用后统一处理
 * @author linfengda
 * @create 2019-02-11 18:00
 */
@FunctionalInterface
public interface LettuceCommandCallback<T> {

    /**
     * 执行redis命令
     * @param commands  命令
     * @return          命令返回结果
     * @throws Exception
     */
    T call(RedisClusterCommands commands) throws Exception;
}
