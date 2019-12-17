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
 * 描述: Hikari连接池配置
 *
 * @author linfengda
 * @create 2018-08-19 23:10
 */
@Configuration
@Slf4j
public class HikariDataSourceConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String passWord;
    @Value("${spring.datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${spring.datasource.idleTimeout}")
    private long idleTimeout;
    @Value("${spring.datasource.maxLifetime}")
    private long maxLifetime;
    @Value("${spring.datasource.connectionTimeout}")
    private long connectionTimeout;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${spring.datasource.connectionTestQuery}")
    private String connectionTestQuery;
    @Value("${spring.datasource.poolName}")
    private String poolName;
    @Value("${spring.datasource.cachePrepStmts}")
    private String cachePrepStmts;
    @Value("${spring.datasource.prepStmtCacheSize}")
    private int prepStmtCacheSize;
    @Value("${spring.datasource.prepStmtCacheSqlLimit}")
    private int prepStmtCacheSqlLimit;

    @Bean(name = "dataSource")
    @Primary
    @Qualifier("dataSource")
    public DataSource primaryDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(passWord);
        config.setIdleTimeout(idleTimeout);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTestQuery(connectionTestQuery);
        config.addDataSourceProperty("useServerPrepStmts", "false");
        config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
        log.info("creating dataSource... dataBase Url:{}",jdbcUrl);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
