package com.linfengda.sb.support.cache.entity.type;

import com.linfengda.sb.support.cache.annotation.DeleteCache;
import com.linfengda.sb.support.cache.annotation.QueryCache;
import com.linfengda.sb.support.cache.annotation.UpdateCache;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;

/**
 * @description: 缓存操作类型
 * @author: linfengda
 * @date: 2020-06-24 18:23
 */
@Getter
@AllArgsConstructor
public enum AnnotationType {
    /**
     * 查询
     */
    QUERY("query", "查询", QueryCache.class),
    /**
     * 更新
     */
    UPDATE("update", "更新", UpdateCache.class),
    /**
     * 删除
     */
    DELETE("delete", "删除", DeleteCache.class),
    ;


    /**
     * 缓存操作
     */
    private String code;
    /**
     * 缓存操作名称
     */
    private String desc;
    /**
     * 对应注解
     */
    private Class<? extends Annotation> annotation;

    /**
     * 是否缓存注解
     * @param clazz 类型
     * @return      true：是，false：否
     */
    public static boolean isCacheAnnotation(Class<? extends Annotation> clazz) {
        for (AnnotationType value : values()) {
            if (value.getAnnotation().getName().equals(clazz.getName())) {
                return true;
            }
        }
        return false;
    }
}
