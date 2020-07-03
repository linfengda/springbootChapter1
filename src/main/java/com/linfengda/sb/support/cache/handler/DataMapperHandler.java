package com.linfengda.sb.support.cache.handler;

import java.util.List;

/**
 * 描述: 请求映射委派模式接口，用于请求分流
 *
 * @author linfengda
 * @create 2020-06-15 14:10
 */
public interface DataMapperHandler {

    /**
     * 请求映射
     * @return  返回映射后的数据
     * @throws Exception
     */
    List<?> doMapper() throws Exception;
}
