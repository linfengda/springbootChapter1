package com.linfengda.sb.chapter1.ctrl;

import com.linfengda.sb.chapter1.common.bean.Result;
import com.linfengda.sb.chapter1.bean.dto.UserUpdateDTO;
import com.linfengda.sb.chapter1.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SystemController {
    @Resource
    private SysUserService sysUserService;


    @PostMapping("/getUserInfo")
    public Result getUserInfo(@NotNull(message = "用户id不能为空") Integer userId) throws Exception {
        return new Result(sysUserService.getUserInfo(userId));
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        sysUserService.updateUserInfo(userUpdateDTO.getUserId(), userUpdateDTO);
        return new Result();
    }

    @PostMapping("/moveUserOrganize")
    public Result moveUserOrganize(@NotNull(message = "用户id不能为空") Integer userId,
                                   @NotNull(message = "部门id不能为空") Integer departmentId,
                                   @NotNull(message = "团队id不能为空") Integer teamId) {
        return new Result();
    }
}
