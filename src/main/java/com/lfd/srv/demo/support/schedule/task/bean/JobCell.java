package com.lfd.srv.demo.support.schedule.task.bean;

import lombok.Data;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * 任务执行的最小单元
 *
 * @author linfengda
 * @date 2021-02-03 16:52
 */
@Data
public class JobCell {
    /**
     * 任务目标实例
     */
    private Object target;
    /**
     * 任务目标方法
     */
    private Method method;
    /**
     * 目标实例类名
     */
    private String targetClassName;
    /**
     * 目标实例方法名
     */
    private String targetMethodName;
    /**
     * 任务
     */
    private String name;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 表达式
     */
    private String cron;
    /**
     * 业务模块
     */
    private String moduleType;
    /**
     * 是否并行
     */
    private boolean distribute;


    public void run() throws SQLException {
        //ReflectionUtils.invokeJdbcMethod(method, target);
    }
}
