package com.linfengda.sb.support.orm;

import com.linfengda.sb.support.orm.entity.AttributeValue;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.base.DefaultFieldSetter;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.orm.sql.builder.PreStatementSqlBuilder;
import com.linfengda.sb.support.orm.sql.builder.PreStatementSql;
import com.linfengda.sb.support.orm.sql.handler.PreStatementSqlHandler;
import com.linfengda.sb.support.orm.utils.ClassUtil;
import com.linfengda.sb.support.orm.utils.ResultSetUtil;
import lombok.Setter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
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
    private DefaultFieldSetter defaultFieldSetter = new DefaultFieldSetter();


    @SuppressWarnings("unchecked")
    public void save(Object po) throws Exception {
        PreStatementSqlHandler statement = null;
        try {
            //batch save
            if (po instanceof List) {
                List<Object> poList = (List<Object>) po;
                this.batchSave(poList);
                return;
            }
            String idName = ClassUtil.getIdName(po.getClass());
            AttributeValue idValue = ClassUtil.getValueByProperty(idName, po);
            PreStatementSql preSql;
            if (idValue.getValue() == null) {
                //simple save
                defaultFieldSetter.addDefaultValue(po);
                preSql = PreStatementSqlBuilder.INSTANCE.buildBatchInsertSql(po);
            } else {
                Long id = (Long) idValue.getValue();
                if (id.longValue() == 0L) {
                    //simple save
                    defaultFieldSetter.addDefaultValue(po);
                    preSql = PreStatementSqlBuilder.INSTANCE.buildBatchInsertSql(po);
                } else {
                    //simple update
                    defaultFieldSetter.updateDefaultValue(po);
                    preSql = PreStatementSqlBuilder.INSTANCE.buildUpdateSql(idValue, po);
                }
            }
            statement = new PreStatementSqlHandler(preSql, dataSource);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * 批量新增
     *
     * @param poList
     * @throws Exception
     */
    public void batchSave(List<Object> poList) throws Exception {
        if (poList == null || poList.size() == 0) {
            return;
        }
        for (Object po : poList) {
            defaultFieldSetter.addDefaultValue(po);
        }

        this.doBatchSave(poList);
    }

    private void doBatchSave(List<Object> poList) throws Exception {
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
        if (idName == null || idName.equals("")) {
            throw new Exception(clazz + " 类没有ID属性");
        }
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add(idName, id);
        update(clazz, setValue, conditionParam);
    }

    public <T> List<T> findAll(ConditionParam param, Class<T> clazz) throws Exception {
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

    public <T> T findOne(ConditionParam param, Class<T> clazz) throws Exception {
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

    public <T> T findByPrimaryKey(Integer id, Class<T> clazz) throws Exception {
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
