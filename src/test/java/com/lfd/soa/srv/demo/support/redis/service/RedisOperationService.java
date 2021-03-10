package com.lfd.soa.srv.demo.support.redis.service;

/**
 * 描述: Redis操作服务
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
public interface RedisOperationService {

    void stringSetOperation() throws Exception;

    void stringSetGetOperation() throws Exception;

    void simpleListOperation() throws Exception;
}
