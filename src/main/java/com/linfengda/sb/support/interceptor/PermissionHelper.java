package com.linfengda.sb.support.interceptor;

/**
 * 描述: 当前sql查询
 *
 * @author linfengda
 * @create 2018-06-27 14:17
 */
public class PermissionHelper {
    private static final ThreadLocal<PermissionSql> LOCAL_PERMISSION_SQL = new ThreadLocal();

    public static void setPermissionSql(PermissionSql permissionSql) {
        LOCAL_PERMISSION_SQL.set(permissionSql);
    }

    public static PermissionSql getPermissionSql() {
        return LOCAL_PERMISSION_SQL.get();
    }

    public static void clearPermissionSql() {
        LOCAL_PERMISSION_SQL.remove();
    }

}
