package com.linfengda.sb.support.api;

import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.api.context.RequestParamContext;
import com.linfengda.sb.support.api.entity.Result;

/**
 * 描述: 基本control控制类
 *
 * @author linfengda
 * @create 2018-08-19 22:51
 */
public class BaseController {

    /** 默认成功的result **/
    public static final Result SUCCESS_RESULT = new Result();

    /**
     * 获取传入参数
     * @return
     * @throws Exception
     */
    protected RequestParam getParams(){
        return RequestParamContext.getParams();
    }

}
