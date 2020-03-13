package com.linfengda.sb.chapter1.common.hystrix.service;

/**
 * 描述: 熔断测试服务
 *
 * @author linfengda
 * @create 2019-04-04 15:01
 */
public interface HystrixService {

    /**
     * 测试超出最大线程数量
     * @param threadId
     * @throws Exception
     */
    void hysOverThread(Integer threadId) throws Exception;

    /**
     * 测试单独线程池
     * @param threadId
     * @throws Exception
     */
    void hysWithThreadPoolKey(Integer threadId) throws Exception;

    /**
     * 测试超时熔断
     * @throws Exception
     */
    void hysTimeOut() throws Exception;

    /**
     * 测试触发熔断的统计窗口条件
     * @param requestId
     * @param isError
     * @throws Exception
     */
    void hysOverVolumeThreshold(Long requestId, Boolean isError) throws Exception;

}
