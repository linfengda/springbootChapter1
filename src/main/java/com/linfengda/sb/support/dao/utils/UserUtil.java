package com.linfengda.sb.support.dao.utils;

/*import cn.dotfashion.common.cache.MobileSessionContext;
import cn.dotfashion.common.cache.SessionContext;*/

/**
 * @Description:
 * @Author: liugenhua
 * @Date created in 2018/5/24 19:03
 */
public class UserUtil {
    public static Long getCurrentUserId(){
        /*if (SessionContext.getCurrentUserId() != null){
            return SessionContext.getCurrentUserId();
        }else {
            return MobileSessionContext.getCurrentUserId();
        }*/
        return -1L;
    }
}
