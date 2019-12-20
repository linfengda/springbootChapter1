package com.linfengda.sb.chapter1.system.api;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.api.BaseController;
import com.linfengda.sb.chapter1.common.api.entity.RequestParam;
import com.linfengda.sb.chapter1.common.api.entity.Result;
import com.linfengda.sb.chapter1.system.entity.dto.UserDTO;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.service.SystemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述: 测试MVC
 *
 * @author linfengda
 * @create 2018-08-16 10:29
 */
@RestController
public class SystemController extends BaseController {
    @Resource
    private SystemService systemService;

    @PostMapping("/sys/pageUserList")
    public Result pageUserList(@Valid UserPageQueryDTO userPageQueryDTO) throws Exception {
        Page<UserListVO> userListVOPage = systemService.pageUserList(userPageQueryDTO);
        return new Result(userListVOPage);
    }

    @PostMapping("/sys/test")
    public Result test(@Valid List<String> userNameList, @NotNull(message = "用户状态不能为空") Integer status) throws Exception {
        System.out.println(userNameList);
        System.out.println(status);
        return SUCCESS_RESULT;
    }

    @PostMapping("/sys/getUserInfo")
    public Result getUserInfo(Long userId) throws Exception {
        return new Result(systemService.getUserInfo(userId));
    }

    @PostMapping("/sys/updateUser")
    public Result updateUser() throws Exception {
        RequestParam params = getParams();
        UserDTO userDTO = JSON.toJavaObject(params, UserDTO.class);
        systemService.updateUser(userDTO.getUserId(), userDTO.getUserName());
        return SUCCESS_RESULT;
    }
}
