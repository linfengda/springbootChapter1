package com.lfd.soa.srv.demo.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.lfd.soa.srv.demo.support.mybatis.MybatisResultInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis plus配置
 * @author linfengda
 * @date 2021-01-21 17:14
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"com.lfd.soa.srv.demo.mapper"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 结果集拦截插件
     */
    @Bean
    public MybatisResultInterceptor mesResultInterceptor() {
        return new MybatisResultInterceptor();
    }

    /**
     * 乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
