package com.linfengda.sb.support.cache.parser;

/**
 * 描述: 缓存注解解析
 *
 * @author linfengda
 * @create 2020-03-24 15:14
 */
public class MyCacheAnnotationParser {
    /**
     * // 获取注解属性
     *         String key = cacheEnable.prefix();
     *         String[] keys = cacheEnable.keys();
     *         long timeOut = cacheEnable.timeOut();
     *         TimeUnit timeUnit = cacheEnable.timeUnit();
     *         // 拼接缓存key
     *         Object[] args = point.getArgs();
     *         Signature signature = point.getSignature();
     *         MethodSignature methodSignature = (MethodSignature) signature;
     *         Class<T> resultType = methodSignature.getReturnType();
     *         String[] parameterNames = methodSignature.getParameterNames();
     *         if (null != keys && keys.length > 0) {
     *             if (null != parameterNames && parameterNames.length > 0) {
     *                 for (int i = 0; i < parameterNames.length; i++) {
     *                     String parameterName = parameterNames[i];
     *                     if (hitKeyName(parameterName, keys)) {
     *                         if (null == args[i]) {
     *                             continue;
     *                         }
     *                         key += ":" + args[i].toString();
     *                     }
     *                 }
     *             }
     *         }
     *         // 从缓存获取结果
     *         Object result = fastCache.getCache(key, resultType);
     *         if (null == result) {
     *             // 从方法获取结果
     *             result = point.proceed();
     *             fastCache.cache(key, result, timeOut, timeUnit);
     *         }
     *         return result;
     */


}
