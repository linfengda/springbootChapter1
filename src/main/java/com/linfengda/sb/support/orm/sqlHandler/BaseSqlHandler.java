package com.linfengda.sb.support.orm.sqlHandler;

import com.linfengda.sb.support.orm.entity.AttributeValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BaseSqlHandler {

    protected PreparedStatement getPreparedStatement(Connection conn, String sql, List<AttributeValue> params) throws SQLException {
        PreparedStatement preStatement = conn.prepareStatement(sql);
        this.setParams(params, preStatement);
        return preStatement;
    }

    protected PreparedStatement getPreparedStatementForBatch(Connection conn, String sql, List<List<AttributeValue>> paramsList) throws SQLException {
        PreparedStatement preStatement = conn.prepareStatement(sql);
        for (List<AttributeValue> params : paramsList) {
            this.setParams(params, preStatement);
            preStatement.addBatch();
        }
        return preStatement;
    }

    private void setParams(List<AttributeValue> params, PreparedStatement preStatement) throws SQLException {
        int index = 1;
        for (AttributeValue param : params) {
            preStatement.setObject(index, param.getValue());
            index++;
        }
    }
}
