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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述: 测试MVC
 *
 * @author linfengda
 * @create 2018-08-16 10:29
 */
@RestController()
@RequestMapping("/pc")
public class SystemController extends BaseController {
    @Resource
    private SystemService systemService;

    @PostMapping("/sys/pageUserList")
    public Result pageUserList(@Validated UserPageQueryDTO userPageQueryDTO) throws Exception {
        Page<UserListVO> userListVOPage = systemService.pageUserList(userPageQueryDTO);
        return new Result(userListVOPage);
    }

    @PostMapping("/sys/test")
    public Result test(@NotNull(message = "数组不能为空") @Size(message = "数组大小不能小于3", min = 3) List userNameList, @NotNull(message = "value不能为空") @Max(message = "value最大值为100", value = 100) Integer value) throws Exception {
        System.out.println(userNameList);
        System.out.println(value);
        return SUCCESS_RESULT;
    }

    @PostMapping("/sys/getUserInfo")
    public Result getUserInfo(@NotNull(message = "用户ID不能为空") Long userId) throws Exception {
        return new Result(systemService.getUserInfo(userId));
    }

    @PostMapping("/sys/updateUser")
    public Result updateUser(@Validated UserDTO userDTO) throws Exception {
        systemService.updateUser(userDTO.getUserId(), userDTO.getUserName());
        return SUCCESS_RESULT;
    }
}
