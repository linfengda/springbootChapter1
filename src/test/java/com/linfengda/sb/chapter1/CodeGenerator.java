package com.linfengda.sb.chapter1;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class CodeGenerator {

    public static void generaterCode(String[] tableNames) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 模板引擎，默认 Veloctiy，如果用的是Veloctiy模板引擎就不需要这句代码
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("linfengda");
        //改成自己的项目路径
        gc.setOutputDir("/Users/linfengda/IdeaProjects/mes-module/mes-srv/src/main/java");
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setControllerName("/api/");

        gc.setSwagger2(true);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！

        gc.setMapperName("%sMapper");
        gc.setXmlName("%sDAOMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sCtrl");


        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】

        });
        //数据源参数改一下
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("backend");
        dsc.setPassword("c3FsOmJhY2tlbg");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/mes?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&transformedBitIsBoolean=true&autoReconnect=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();




        // strategy.setTablePrefix(new String[] { "hunter_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setEntityLombokModel(true); // 启用lombok增加实体类的get，set方法简化代码；如果不启用可以改为false
        //strategy.setSuperEntityClass(BaseIncrementEntity.class);



        strategy.setSuperEntityColumns("id","create_uid","create_uname","create_time","update_uid","update_uname","update_time","delete_tag","version");

        strategy.setInclude(tableNames); // 需要生成的表

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //在哪个父包下生成  改成自己的
        pc.setParent("com.chicv.mineral.srv.mes");
        pc.setEntity("bean.entity");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("ctrl");

        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }


    /**
     * 这里代码生成的表必须都注释旧的，不允许把旧的代码给重新生成覆盖。
     * @param args
     */
    public static void main(String[] args) {

        String[] tables =new String[]{"produce_bill_qc","produce_bill_deduction"};
        //String[] tables =new String[]{"base_remark","base_operate"};

        generaterCode(tables);
    }

}
