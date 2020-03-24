package com.linfengda.sb.chapter1.system.api;

import com.linfengda.sb.chapter1.common.api.BaseController;
import com.linfengda.sb.chapter1.common.api.entity.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述: API参数校验
 *
 * @author linfengda
 * @create 2020-03-24 17:11
 */
@RestController()
@RequestMapping("/pc/apiValid")
public class ApiParameterValidateController extends BaseController {

    @PostMapping("/test")
    public Result test(@NotNull(message = "数组不能为空") @Size(message = "数组大小不能小于3", min = 3) List userNameList, @NotNull(message = "value不能为空") @Max(message = "value最大值为100", value = 100) Integer value) {
        System.out.println(userNameList);
        System.out.println(value);
        return SUCCESS_RESULT;
    }
}
