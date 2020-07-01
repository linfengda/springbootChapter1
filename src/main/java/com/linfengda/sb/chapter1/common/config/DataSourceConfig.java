package com.linfengda.sb.chapter1.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 描述: 数据库连接池
 *
 * @author linfengda
 * @create 2019-05-21 10:30
 */
@Configuration
@Slf4j
public class DataSourceConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String passWord;
    @Value("${spring.datasource.hikari.pool-name}")
    private String poolName;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private long idleTimeout;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private long maxLifetime;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private long connectionTimeout;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minIdle;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;



    @Bean(name = "dataSource")
    @Primary
    @Qualifier("dataSource")
    public DataSource primaryDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(passWord);
        config.setPoolName(poolName);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setConnectionTimeout(connectionTimeout);
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTestQuery(connectionTestQuery);
        log.info("creating dataSource... dataBase Url:{}",jdbcUrl);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
