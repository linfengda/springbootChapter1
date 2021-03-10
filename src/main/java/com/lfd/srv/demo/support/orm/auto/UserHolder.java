package com.lfd.srv.demo.support.orm.auto;

import com.lfd.common.exception.BusinessException;

/**
 * 描述: 获取当前操作人
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public final class UserHolder {
    private static UserAware userAware;

    /**
     * 需要调用这个方法初始化Session获取途径
     * @param userAware aware user info
     */
    public static void init(UserAware userAware) {
        if (null == userAware) {
            throw new BusinessException("没有UserAware的实例！");
        }
        UserHolder.userAware = userAware;
    }

    /**
     * 获取当前uid
     * @return  当前uid
     */
    public static String getCurrentUid(){
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
