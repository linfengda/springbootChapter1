package com.linfengda.sb.support.orm;

import com.linfengda.sb.support.orm.auto.BaseEntity;
import com.linfengda.sb.support.orm.entity.*;
import com.linfengda.sb.support.orm.exception.DataAccessException;
import com.linfengda.sb.support.orm.sql.builder.PreStatementSql;
import com.linfengda.sb.support.orm.sql.builder.PreStatementSqlBuilder;
import com.linfengda.sb.support.orm.sql.handler.PreStatementSqlHandler;
import com.linfengda.sb.support.orm.utils.ClassUtil;
import com.linfengda.sb.support.orm.utils.PageUtil;
import com.linfengda.sb.support.orm.utils.ResultSetUtil;
import lombok.Setter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

/**
 * 描述: 基础ORM服务
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public abstract class AbstractBaseService {
    @Setter
    @Resource
    private DataSource dataSource;


    public void save(Object po) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            //batch save
            if (po instanceof List) {
                throw new DataAccessException("请使用batchSave方法保存list对象！");
            }
            String idName = ClassUtil.getIdName(po.getClass());
            AttributeValue idValue = ClassUtil.getValueByProperty(idName, po);
            PreStatementSql preSql;
            if (idValue.getValue() == null) {
                //simple save
                if (po instanceof BaseEntity) {
                    ((BaseEntity) po).onCreate();
                }
                preSql = PreStatementSqlBuilder.INSTANCE.buildInsertSql(po);
            } else {
                //simple update
                if (po instanceof BaseEntity) {
                    ((BaseEntity) po).onUpdate();
                }
                preSql = PreStatementSqlBuilder.INSTANCE.buildUpdateSql(idValue, po);
            }
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void batchSave(List<Object> poList) throws Exception {
        if (poList == null || poList.size() == 0) {
            return;
        }
        for (Object po : poList) {
            if (po instanceof BaseEntity) {
                ((BaseEntity) po).onCreate();
            }
        }
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildBatchInsertSql(poList);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeBatchSave();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void update(Class<?> clazz, SetValue setValue, ConditionParam conditionParam) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildUpdateSql(setValue, conditionParam, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void updateByPrimaryKey(Class<?> clazz, SetValue setValue, Integer id) throws Exception {
        String idName = ClassUtil.getIdName(clazz);
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add(idName, id);
        update(clazz, setValue, conditionParam);
    }

    public <T> PageResult<T> page(ConditionParam param, Class<T> clazz) throws Exception{
        PreStatementSqlHandler statement = null;
        PageResult<T> pageResult = new PageResult<>();
        try {
            long totalRecord = this.countByParam(param,clazz);
            int totalPage = PageUtil.getTotalPage(param.getPageSize(),totalRecord);
            if (totalRecord == 0L){
                pageResult.setPageNo(param.getPageNo());
                pageResult.setPageSize(param.getPageSize());
                pageResult.setTotalRecord(totalRecord);
                pageResult.setTotalPage(totalPage);
                pageResult.setRecorders(Collections.EMPTY_LIST);
                return pageResult;
            }
            if (param.getPageNo() > totalPage) {
                throw new DataAccessException("当前查询页数超过最大页数，pageNo：" + param.getPageNo() + "，totalPage：" + totalPage);
            }
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param, clazz, true);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            List records = ResultSetUtil.convertToListObject(clazz, result);

            pageResult.setPageNo(param.getPageNo());
            pageResult.setPageSize(param.getPageSize());
            pageResult.setTotalRecord(totalRecord);
            pageResult.setTotalPage(totalPage);
            pageResult.setRecorders(records);
            return pageResult;
        }finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public <T> List<T> query(ConditionParam param, Class<T> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param, clazz, false);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            List<T> recorders = ResultSetUtil.convertToListObject(clazz, result);

            return recorders;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public <T> T get(ConditionParam param, Class<T> clazz) throws Exception {
        param.setPageNo(1);
        param.setPageSize(1);
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.getQuerySqlByConditionParam(param, clazz, true);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.convertToObject(clazz, result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public <T> T getByPrimaryKey(Integer id, Class<T> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildQuerySql(id, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.convertToObject(clazz, result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public long countByParam(ConditionParam param, Class<?> clazz) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            PreStatementSql preSql = PreStatementSqlBuilder.INSTANCE.buildCountSql(param, clazz);
            statement = new PreStatementSqlHandler(preSql, dataSource);
            ResultSet result = statement.executeQuery();
            return ResultSetUtil.getLong("c", result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public boolean isExist(ConditionParam param, Class<?> clazz) throws Exception {
        Long count = this.countByParam(param, clazz);
        return count.longValue() >= 1L;
    }
}
