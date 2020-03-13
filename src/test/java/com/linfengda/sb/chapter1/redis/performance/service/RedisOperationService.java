package com.linfengda.sb.chapter1.redis.performance.service;

/**
 * 描述: Redis操作服务
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
public interface RedisOperationService {

    void simpleStringOperation(int count) throws Exception;

    void stringSetOperation() throws Exception;

    void stringSetGetOperation() throws Exception;

    void simpleListOperation() throws Exception;
}
