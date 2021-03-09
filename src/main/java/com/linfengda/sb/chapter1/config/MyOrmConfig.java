package com.linfengda.sb.chapter1.config;

import com.linfengda.sb.support.gateway.session.UserSession;
import com.linfengda.sb.support.orm.OrmTemplate;
import com.linfengda.sb.support.orm.auto.UserAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author linfengda
 * @date 2021-03-08 11:35
 */
@Configuration
public class MyOrmConfig {
    @Resource
    private DataSource dataSource;

    /**
     * 注入ORM框架工具类实例
     * @return	ORM框架工具类实例
     */
    @Bean
    public OrmTemplate ormTemplate() {
        OrmTemplate ormTemplate = new OrmTemplate();
        ormTemplate.setDataSource(dataSource);
        return ormTemplate;
    }

    /**
     * 注入ORM框架工具类实例
     * @return	ORM框架工具类实例
     */
    @Bean
    public UserAware userAware() {
        return new UserAware() {
            @Override
            public String getCurrentUid() {
                return UserSession.getUserId();
            }

            @Override
            public String getCurrentUName() {
                return UserSession.getUserName();
            }
        };
    }
}
