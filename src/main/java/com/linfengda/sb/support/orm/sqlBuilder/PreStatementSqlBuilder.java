package com.linfengda.sb.support.orm.sqlBuilder;

import com.linfengda.sb.support.orm.entity.AttributeValue;
import com.linfengda.sb.support.orm.entity.ConditionParam;
import com.linfengda.sb.support.orm.entity.DefaultField;
import com.linfengda.sb.support.orm.entity.SetValue;
import com.linfengda.sb.support.orm.exception.DataAccessException;
import com.linfengda.sb.support.orm.sqlHandler.PreStatementSql;
import com.linfengda.sb.support.orm.utils.ClassUtil;
import com.linfengda.sb.support.orm.utils.UserUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public enum PreStatementSqlBuilder {
    INSTANCE;

    public PreStatementSql buildBatchInsertSql(Object po) throws Exception {
        Class clazz = po.getClass();
        List<Field> fields = ClassUtil.getFields(clazz);
        String tableName = ClassUtil.getTableName(clazz);

        PreStatementSql preSql = new PreStatementSql();
        // set sql
        preSql.setSql(getInsertSqlStr(fields, tableName));
        // set params
        List<AttributeValue> params = new ArrayList<>();
        for (Field field : fields) {
            AttributeValue aValue = ClassUtil.getValueByProperty(field.getName(), po, po.getClass());
            params.add(aValue);
        }
        preSql.setParams(params);

        return preSql;
    }

    public PreStatementSql buildBatchInsertSql(List<Object> poList) throws Exception {
        Class clazz = poList.get(0).getClass();
        List<Field> fields = ClassUtil.getFields(clazz);
        String tableName = ClassUtil.getTableName(clazz);

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

    public PreStatementSql buildUpdateSql(AttributeValue idValue, Object po) throws Exception {
        PreStatementSql preSql = new PreStatementSql();
        Class clazz = po.getClass();
        List<Field> fields = ClassUtil.getFields(clazz);
        preSql.setSql(getUpdateSqlStr(fields, clazz));

        List<AttributeValue> params = new ArrayList<>();
        for (Field field : fields) {
            AttributeValue value = ClassUtil.getValueByProperty(field.getName(), po, clazz);
            params.add(value);
        }
        params.add(idValue);

        AttributeValue versionValue = ClassUtil.getValueByProperty("version", po, clazz);
        if (versionValue == null) {
            throw new DataAccessException("po实体version为空");
        }
        Integer version = (Integer) versionValue.getValue();
        version = version - 1;
        versionValue.setValue(version);
        params.add(versionValue);

        preSql.setParams(params);
        return preSql;
    }

    public PreStatementSql buildUpdateSql(SetValue setValue, ConditionParam conditionParam, Class<?> clazz) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName(clazz));
        List<Field> fields = ClassUtil.getFields(clazz);
        for (Field field : fields) {
            if (field.getName().equals(DefaultField.UPDATE_TIME)) {
                setValue.add(DefaultField.UPDATE_TIME, new Timestamp(System.currentTimeMillis()));
            }
            if (field.getName().equals(DefaultField.UPDATE_USER)) {
                setValue.add(DefaultField.UPDATE_USER, UserUtil.getCurrentUserId());
            }
        }
        sql.append(setValue.getPartSql());
        sql.append(conditionParam.getConditionSql());

        List<AttributeValue> params = new ArrayList<>();
        for (AttributeValue attr : setValue.getParams()) {
            params.add(attr);
        }
        for (AttributeValue attr : conditionParam.getParams()) {
            params.add(attr);
        }

        PreStatementSql preSql = new PreStatementSql();
        preSql.setSql(sql.toString());
        preSql.setParams(params);
        return preSql;
    }

    public PreStatementSql buildQuerySql(Integer Id, Class<?> clazz) throws Exception {
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

    public PreStatementSql getQuerySqlByConditionParam(ConditionParam param, Class<?> clazz, boolean isUsePage) throws Exception {
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

    public PreStatementSql buildCountSql(ConditionParam param, Class<?> clazz) throws Exception {
        PreStatementSql preSql = new PreStatementSql();
        String tableName = getTableName(clazz);

        StringBuilder sql = new StringBuilder("SELECT COUNT(1) as c FROM ");
        sql.append(tableName);

        preSql.setSql(sql + param.getConditionSql());
        preSql.setParams(param.getParams());
        return preSql;
    }

    private String getInsertSqlStr(List<Field> fields, String tableName) throws Exception {
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

    private String getUpdateSqlStr(List<Field> fields, Class<?> clazz) throws Exception {
        int len = fields.size();
        String tableName = getTableName(clazz);
        StringBuilder sql = new StringBuilder("UPDATE " + tableName);
        sql.append(" SET ");
        for (int i = 0; i < len; i++) {
            if (i == len - 1) {
                sql.append(ClassUtil.convert(fields.get(i).getName())).append("=?");
            } else {
                sql.append(ClassUtil.convert(fields.get(i).getName())).append("=?").append(",");
            }
        }
        sql.append(" WHERE ").append(ClassUtil.convert(ClassUtil.getIdName(clazz))).append("=?");
        sql.append(" AND version=?");
        return sql.toString();
    }

    private String getTableName(Class<?> clazz) throws Exception {
        String tableName = ClassUtil.getTableName(clazz);
        if (tableName == null || tableName.equals("none")) {
            throw new Exception("对象类型没有添加表名注解!");
        }

        return tableName;
    }

    private String getIdName(Class<?> clazz) throws Exception {
        String idName = ClassUtil.getIdName(clazz);
        if (idName == null || idName.equals("")) {
            throw new Exception(clazz + " 类没有ID属性");
        }
        idName = ClassUtil.convert(idName);

        return idName;
    }
}
