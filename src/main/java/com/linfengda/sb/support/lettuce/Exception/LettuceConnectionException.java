package com.linfengda.sb.support.lettuce.Exception;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: Lettuce客户端连接异常
 *
 * @author linfengda
 * @create 2019-02-18 22:22
 */
@Slf4j
public class LettuceConnectionException extends RedisException {

    public LettuceConnectionException(String msg) {
        super(msg);
    }
}
