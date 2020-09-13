package com.linfengda.sb.chapter1.system.api;

import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.common.api.BaseController;
import com.linfengda.sb.chapter1.common.api.entity.Result;
import com.linfengda.sb.chapter1.system.entity.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.system.entity.dto.UserPageQueryDTO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.service.SysUserService;
import com.linfengda.sb.support.apivalidator.annotation.ApiValidator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 描述: 系统管理controller
 *
 * @author linfengda
 * @create 2018-08-16 10:29
 */
@RestController()
@RequestMapping("/pc/sys")
public class SystemController extends BaseController {
    @Resource
    private SysUserService sysUserService;


    @PostMapping("/pageUserList")
    public Result pageUserList(@ApiValidator @RequestBody UserPageQueryDTO userPageQueryDTO) throws Exception {
        Page<UserListVO> userListVOPage = sysUserService.pageUserList(userPageQueryDTO);
        return new Result(userListVOPage);
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo(@NotNull(message = "用户id不能为空") Integer userId) throws Exception {
        return new Result(sysUserService.getUserInfo(userId));
    }

    @PostMapping("/updateUser")
    public Result updateUser(@ApiValidator @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        sysUserService.updateUser(userUpdateDTO.getUserId(), userUpdateDTO.getUserName());
        return SUCCESS_RESULT;
    }

    @GetMapping("/moveUserOrganize")
    public Result moveUserOrganize(@NotNull(message = "用户id不能为空") Integer userId,
                                   @NotNull(message = "部门id不能为空") Integer departmentId,
                                   @NotNull(message = "团队id不能为空") Integer teamId) {
        return SUCCESS_RESULT;
    }
}
