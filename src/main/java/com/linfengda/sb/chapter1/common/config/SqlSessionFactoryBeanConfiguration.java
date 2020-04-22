package com.linfengda.sb.chapter1.common.config;

import com.github.pagehelper.PageInterceptor;
import com.linfengda.sb.support.orm.OrmTemplate;
import com.linfengda.sb.support.interceptor.PermissionInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 描述: SessionFactory配置
 *
 * @author linfengda
 * @create 2018-08-19 23:10
 */
@SpringBootConfiguration
public class SqlSessionFactoryBeanConfiguration {
    @Resource
    private DataSource dataSource;

	/**
	 * mybatis SqlSessionFactory配置
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //配置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);

        //指定xml配置文件所在的路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));

        //指定拦截器
		Interceptor[] interceptors = new Interceptor[2];
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties properties = new Properties();
		properties.setProperty("helperDialect", "mysql");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("params", "count=countSql");
		pageInterceptor.setProperties(properties);
		interceptors[0] = pageInterceptor;
		interceptors[1] = new PermissionInterceptor();
		sqlSessionFactoryBean.setPlugins(interceptors);
		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * ORM框架支持类
	 * @return
	 * @throws Exception
	 */
	@Bean
	public OrmTemplate ormTemplate() throws Exception {
		OrmTemplate ormTemplate = new OrmTemplate();
		ormTemplate.setDataSource(dataSource);
		return ormTemplate;
	}
}
