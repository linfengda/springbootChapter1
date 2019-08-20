package com.linfengda.sb.support.interceptor;

import lombok.Data;

import java.io.Closeable;
import java.io.IOException;

/**
 * 描述: sql权限信息（如果没有where关键字，将默认添加到sql结尾）
 *
 * @author linfengda
 * @create 2018-06-27 14:17
 */
@Data
public class PermissionSql implements Closeable {

    private Integer whereIndex;
    private String groupField;
    private String stationField;

    public PermissionSql(Integer whereIndex,
                         String groupField,
                         String stationField) {
        this.whereIndex = whereIndex;
        this.groupField = groupField;
        this.stationField = stationField;
    }

    @Override
    public void close() throws IOException {
        PermissionHelper.clearPermissionSql();
    }

}
