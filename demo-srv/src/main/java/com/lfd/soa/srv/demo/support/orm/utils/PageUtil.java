package com.lfd.soa.srv.demo.support.orm.utils;

/**
 * 描述: 分页工具
 *
 * @author linfengda
 * @create 2019-04-12 13:25
 */
public class PageUtil {

    public static int getTotalPage(int pageSize,long totalRecorder){
        if(totalRecorder == 0){
            return  0;
        }else {
            if (totalRecorder%pageSize == 0){
                return  (int)totalRecorder/pageSize;
            }else {
                return  (int)totalRecorder/pageSize+1;
            }
        }
    }
}
