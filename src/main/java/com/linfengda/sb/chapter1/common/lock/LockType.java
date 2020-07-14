package com.linfengda.sb.chapter1.common.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述: 业务锁类型
 *
 * @author linfengda
 * @create 2020-03-24 11:38
 */
@AllArgsConstructor
@Getter
public enum LockType {
    /**
     * 加载部门组织树缓存业务锁
     */
    LOAD_DEPARTMENT_TREE_LOCK("sys:dept:tree:", "加载部门组织树缓存业务锁");


    /**
     * 业务锁前缀
     */
    private String prefix;
    /**
     * 业务锁名称
     */
    private String name;
}
