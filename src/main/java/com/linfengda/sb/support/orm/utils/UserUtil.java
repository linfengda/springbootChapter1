package com.linfengda.sb.support.orm.utils;

import com.linfengda.sb.support.orm.aware.UserAware;

/**
 * 描述: 获取当前操作人
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public final class UserUtil {
    private static UserAware userAware;

    /**
     * 需要调用这个方法初始化Session获取途径
     * @param userAware 用户Session
     */
    public static void init(UserAware userAware) {
        UserUtil.userAware = userAware;
    }

    /**
     * 获取当前uid
     * @return  当前uid
     */
    public static Integer getCurrentUid(){
        return userAware.getCurrentUid();
    }

    /**
     * 获取当前uName
     * @return  当前uid
     */
    public static String getCurrentUName(){
        return userAware.getCurrentUName();
    }
}
