package com.linfengda.sb.chapter1.lambdaTest;

import lombok.Data;

/**
 * 描述: 定义回调函数
 *
 * @author linfengda
 * @create 2019-01-24 16:14
 */
@Data
public class MyCallBack {
    private String mySelf;
    private String mySon;

    boolean callMyself() throws Exception {
        System.out.println("---------------------------------call " + mySelf);
        return true;
    }

    boolean callMyson() throws Exception {
        System.out.println("---------------------------------call " + mySon);
        return true;
    }

}
