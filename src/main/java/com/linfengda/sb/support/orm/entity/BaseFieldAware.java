package com.linfengda.sb.support.orm.entity;

/**
 * @description: 利用策略模式设置基础属性，以便在不同项目中应用框架
 * @author: linfengda
 * @date: 2020-11-28 21:51
 */
public interface BaseFieldAware {

    /**
     * 创建po监听
     */
    void onCreate();

    /**
     * 更新po监听
     */
    void onUpdate();
}
