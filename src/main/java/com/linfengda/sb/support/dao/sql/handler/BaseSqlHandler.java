package com.linfengda.sb.support.dao.sql.handler;

import com.linfengda.sb.support.dao.entity.AttributeValue;
import com.linfengda.sb.support.dao.entity.type.SimpleBaseType;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/6/11 9:27
 */
public class BaseSqlHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected PreparedStatement getPreparedStatement(Connection conn,String sql,List<AttributeValue> params) throws SQLException {
        PreparedStatement preStatement = conn.prepareStatement(sql);
        this.setParams(params, preStatement);
        return preStatement;
    }

    protected PreparedStatement getPreparedStatementForBatch(Connection conn,String sql,List<List<AttributeValue>> paramsList) throws SQLException {
        PreparedStatement preStatement = conn.prepareStatement(sql);
        for (List<AttributeValue> params : paramsList) {
            this.setParams(params, preStatement);
            preStatement.addBatch();
        }
        return preStatement;
    }

    private void setParams(List<AttributeValue> params,PreparedStatement preStatement) throws SQLException {
        int k = 1;
        for (AttributeValue attributeValue : params) {
            SimpleBaseType type = SimpleBaseType.getBaseType(attributeValue.getType());

            switch (type) {
                case STRING:
                    preStatement.setString(k, (String)attributeValue.getValue());
                    break;
                case INT:
                    preStatement.setObject(k,attributeValue.getValue());
                    break;
                case LONG:
                    preStatement.setObject(k,attributeValue.getValue());
                    break;
                case DOUBLE:
                    preStatement.setObject(k,attributeValue.getValue());
                    break;
                case FLOAT:
                    preStatement.setObject(k,attributeValue.getValue());
                    break;
                case DATE:
                    if(attributeValue.getValue() != null) {
                        java.util.Date d = (java.util.Date)attributeValue.getValue();
                        preStatement.setObject(k, dateFormat.format(d));
                    } else {
                        preStatement.setTimestamp(k, null);
                    }
                    break;
                case BIG_DECIMAL:
                    preStatement.setBigDecimal(k,(BigDecimal)attributeValue.getValue());
                    break;
                case SQL_DATE:
                    Date dt = (Date) attributeValue.getValue();
                    preStatement.setDate(k,dt);
                    break;
                case SQL_DATE_TIME:
                    Timestamp t = (Timestamp) attributeValue.getValue();
                    if (t != null){
                        preStatement.setObject(k,dateFormat.format(t));
                    }else {
                        preStatement.setObject(k,null);
                    }
                    break;
                case BYTE:
                    preStatement.setByte(k,(Byte)attributeValue.getValue());
                    break;
                case BYTES:
                    preStatement.setBytes(k,(byte [])attributeValue.getValue());
                    break;
                default:
                    break;
            }
            k++;
        }
    }
}
