package com.lfd.soa.srv.demo.support.orm.auto;

/**
 * 描述: 获取当前操作人
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public interface UserAware {

    /**
     * 获取当前uid
     * @return  当前uid
     */
    String getCurrentUid();

    /**
     * 获取当前uName
     * @return  当前uid
     */
    String getCurrentUName();
}
