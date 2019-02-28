package com.linfengda.sb.support.lettuce.test.multi;

/**
 * 定义延迟基准测试
 */
public interface BenchmarkConstant {
    /**
     * 定义测试线程数量
     */
    int THREAD_NUM = 10;
    /**
     * 定义测试时长
     */
    long TEST_SECONDS = 60000;
    /**
     * 定义延迟阈值
     */
    long SLOW_MILLISECONDS = 30;
}
