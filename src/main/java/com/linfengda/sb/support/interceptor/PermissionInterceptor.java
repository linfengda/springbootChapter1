package com.linfengda.sb.support.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 描述: mybatis查询拦截
 *
 * @author linfengda
 * @create 2018-06-27 14:17
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Slf4j
public class PermissionInterceptor implements Interceptor {
    /**
     * 拦截的第一个query方法参数数量
     */
    private static final int QUERY_METHOD0_ARGS_LEN = 4;
    /**
     * WHERE关键字截取长度
     */
    private static final int FIVE = 5;
    /**
     * WHERE查询关键字
     */
    private static final String LOWER_WHERE = "where";
    /**
     * WHERE查询关键字
     */
    private static final String UPPER_WHERE = "WHERE";
    /**
     * 父权限和子权限取集合方式
     */
    private static final String MERGE_TYPE = "OR";
    /**
     * 权限sql合并原sql方式
     */
    private static final String PERMISSION_MERGE_TYPE = "AND";



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = null;
        try {
            PermissionSql permissionSql = PermissionHelper.getPermissionSql();
            if (null != permissionSql) {
                Executor executor = (Executor)invocation.getTarget();
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement)args[0];
                Object parameter = args[1];
                RowBounds rowBounds = (RowBounds)args[2];
                ResultHandler resultHandler = (ResultHandler)args[3];
                CacheKey cacheKey;
                BoundSql boundSql;
                if (args.length == QUERY_METHOD0_ARGS_LEN) {
                    boundSql = ms.getBoundSql(parameter);
                    cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
                } else {
                    cacheKey = (CacheKey)args[4];
                    boundSql = (BoundSql)args[5];
                }

                // 获取权限限制sql
                String permissionSqlStr = getPermissionSql(permissionSql);

                // 获取完整sql
                String sql = insertPermissionSql(boundSql, permissionSql.getWhereIndex(), permissionSqlStr);

                // 通过反射覆盖原sql
                Field sqlField = boundSql.getClass().getDeclaredField("sql");
                sqlField.setAccessible(true);
                sqlField.set(boundSql, sql);

                // 执行查询
                result = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            }else {
                result = invocation.proceed();
            }
        } finally {
            PermissionHelper.clearPermissionSql();
        }
        return result;
    }

    /**
     * 获取权限限制sql，包括生产组权限和工位权限
     *
     * @param permissionSql
     * @return
     * @throws Exception
     */
    private String getPermissionSql(PermissionSql permissionSql) throws Exception {
        String permissionSqlStr = "";
        Set<Long> productionTeamIdList = new HashSet<>();
        Set<Long> stationIdList = new HashSet<>();
        if (CollectionUtils.isEmpty(productionTeamIdList) && CollectionUtils.isEmpty(stationIdList)) {
            permissionSqlStr = " 0 = 1 ";
        } else {
            permissionSqlStr = getPermissionSql(permissionSqlStr,
                    permissionSql.getGroupField(), productionTeamIdList);
            permissionSqlStr = getPermissionSql(permissionSqlStr,
                    permissionSql.getStationField(), stationIdList);
            permissionSqlStr = " (" + permissionSqlStr + ") ";
        }
        return permissionSqlStr;
    }

    /**
     * 获取权限限制sql，条件使用in查询，不同字段使用or查询
     *
     * @param permissionSql
     * @param permissionField
     * @param permissionIds
     * @return
     * @throws Exception
     */
    private String getPermissionSql(String permissionSql,
                                    String permissionField,
                                    Set<Long> permissionIds) throws Exception {
        if (StringUtils.isBlank(permissionField) || CollectionUtils.isEmpty(permissionIds)) {
            return permissionSql;
        }
        Long[] permissionArray = permissionIds.toArray(new Long[permissionIds.size()]);
        String permissionStr = Arrays.toString(permissionArray);
        permissionStr = permissionStr.substring(1, permissionStr.length() - 1);
        if (StringUtils.isBlank(permissionSql)) {
            permissionSql += permissionField + " IN(" + permissionStr + ")";
        } else {
            permissionSql += " "+ MERGE_TYPE +" " + permissionField + " IN(" + permissionStr + ")";
        }
        return permissionSql;
    }

    /**
     * 插入权限限制sql
     *
     * @param boundSql
     * @param whereIndex
     * @param permissionSql
     * @return
     */
    private String insertPermissionSql(BoundSql boundSql, int whereIndex, String permissionSql) {
        String sql = boundSql.getSql().replaceAll(LOWER_WHERE, UPPER_WHERE);
        if (sql.contains(UPPER_WHERE)) {
            if (whereIndex == 1) {
                sql = sql.substring(0, sql.indexOf(UPPER_WHERE) + FIVE) + permissionSql + PERMISSION_MERGE_TYPE + sql.substring(sql.indexOf(UPPER_WHERE) + FIVE, sql.length());
            } else {
                int count = 0;
                int idx = 0;
                while ((idx = sql.indexOf(UPPER_WHERE, idx)) != -1 && (++count) != whereIndex) {idx += FIVE;}
                if (count == whereIndex) {
                    sql = sql.substring(0, idx + FIVE) + permissionSql + PERMISSION_MERGE_TYPE + sql.substring(idx + FIVE, sql.length());
                }
            }
        } else {
            sql += "\n" + UPPER_WHERE + permissionSql;
        }
        return sql;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
