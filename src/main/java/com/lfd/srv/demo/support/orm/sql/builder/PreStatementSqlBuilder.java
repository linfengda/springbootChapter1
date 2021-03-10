package com.lfd.srv.demo.support.orm.sql.builder;

import com.lfd.srv.demo.support.orm.entity.AttributeValue;
import com.lfd.srv.demo.support.orm.entity.ConditionParam;
import com.lfd.srv.demo.support.orm.entity.SetValue;
import com.lfd.srv.demo.support.orm.utils.ClassUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: build sql
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public enum PreStatementSqlBuilder {
    /**
     * 实例
     */
    INSTANCE;

    public PreStatementSql buildInsertSql(Object po) {
        Class<?> clazz = po.getClass();
        String tableName = ClassUtil.getTableName(clazz);
        List<Field> fields = ClassUtil.getFields(clazz);
        fields = getNotNullField(fields, po, clazz);

        PreStatementSql preSql = new PreStatementSql();
        // set sql
        preSql.setSql(getInsertSqlStr(fields, tableName));
        // set params
        List<AttributeValue> params = new ArrayList<>();
        for (Field field : fields) {
            AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, clazz);
            params.add(aValue);
        }
        preSql.setParams(params);

        return preSql;
    }

    public PreStatementSql buildBatchInsertSql(List<Object> poList) {
        Class<?> clazz = poList.get(0).getClass();
        String tableName = ClassUtil.getTableName(clazz);
        List<Field> fields = ClassUtil.getFields(clazz);

        PreStatementSql preSql = new PreStatementSql();
        // set sql
        preSql.setSql(getInsertSqlStr(fields, tableName));
        // set params
        List<List<AttributeValue>> poParams = new ArrayList<>();
        for (Object po : poList) {
            List<AttributeValue> params = new ArrayList<>();
            for (Field field : fields) {
                AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, po.getClass());
                params.add(aValue);
            }
            poParams.add(params);
        }
        preSql.setBatchParams(poParams);

        return preSql;
    }

    public PreStatementSql buildUpdateSql(AttributeValue idValue, Object po) {
        Class<?> clazz = po.getClass();
        List<Field> fields = ClassUtil.getFields(clazz);
        SetValue setValue = new SetValue();
        for (Field field : fields) {
            AttributeValue value = ClassUtil.getValueByProperty(field.getName(), po, clazz);
            if (null == value) {
                continue;
            }
            setValue.add(field.getName(), value.getValue());
        }
        String idName = ClassUtil.getIdName(clazz);
        ConditionParam conditionParam = new ConditionParam();
        conditionParam.add(idName, idValue.getValue());

        PreStatementSql preSql = buildUpdateSql(setValue, conditionParam, clazz);
        return preSql;
    }

    public PreStatementSql buildUpdateSql(SetValue setValue, ConditionParam conditionParam, Class<?> clazz) {
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName(clazz));
        sql.append(setValue.getUpdateSql());
        sql.append(conditionParam.getConditionSql());

        List<AttributeValue> params = new ArrayList<>();
        params.addAll(setValue.getParams());
        params.addAll(conditionParam.getParams());

        PreStatementSql preSql = new PreStatementSql();
        preSql.setSql(sql.toString());
        preSql.setParams(params);
        return preSql;
    }

    public PreStatementSql buildQuerySql(Integer Id, Class<?> clazz) {
        PreStatementSql preSql = new PreStatementSql();
        String tableName = getTableName(clazz);
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName + " ");
        sql.append("WHERE " + getIdName(clazz) + " = ?;");

        // 参数
        List<AttributeValue> params = new ArrayList<>();
        params.add(new AttributeValue(Id, "java.lang.Integer"));

        preSql.setSql(sql.toString());
        preSql.setParams(params);
        return preSql;
    }

    public PreStatementSql getQuerySqlByConditionParam(ConditionParam param, Class<?> clazz, boolean isUsePage) {
        PreStatementSql preSql = new PreStatementSql();
        String tableName = getTableName(clazz);

        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName);

        if (isUsePage) {
            preSql.setSql(sql + param.getPageConditionSql());
        } else {
            preSql.setSql(sql + param.getNotPageConditionSql());
        }

        preSql.setParams(param.getParams());
        return preSql;
    }

    public PreStatementSql buildCountSql(ConditionParam param, Class<?> clazz) {
        PreStatementSql preSql = new PreStatementSql();
        String tableName = getTableName(clazz);

        StringBuilder sql = new StringBuilder("SELECT COUNT(1) as c FROM ");
        sql.append(tableName);

        preSql.setSql(sql + param.getConditionSql());
        preSql.setParams(param.getParams());
        return preSql;
    }

    private String getInsertSqlStr(List<Field> fields, String tableName) {
        int len = fields.size();
        // 追加字段名
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        sql.append(tableName + "(");
        for (int i = 0; i < len; i++) {
            if (i == len - 1) {
                sql.append(ClassUtil.convert(fields.get(i).getName()));
            } else {
                sql.append(ClassUtil.convert(fields.get(i).getName()) + ",");
            }
        }

        // 追加参数占位符
        sql.append(") VALUES(");
        for (int i = 0; i < len; i++) {
            if (i == len - 1) {
                sql.append("?");
            } else {
                sql.append("?,");
            }
        }
        sql.append(");");

        return sql.toString();
    }

    private String getTableName(Class<?> clazz) {
        String tableName = ClassUtil.getTableName(clazz);
        return tableName;
    }

    private String getIdName(Class<?> clazz) {
        String idName = ClassUtil.getIdName(clazz);
        idName = ClassUtil.convert(idName);
        return idName;
    }

    private List<Field> getNotNullField(List<Field> fields, Object po, Class<?> clazz) {
        List<Field> notNullFields = new ArrayList<>();
        for (Field field : fields) {
            AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, clazz);
            if (null == aValue.getValue()) {
                continue;
            }
            notNullFields.add(field);
        }
        return notNullFields;
    }
}
