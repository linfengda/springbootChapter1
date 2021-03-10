package com.lfd.soa.srv.demo.ctrl;


import com.lfd.soa.common.bean.Result;
import com.lfd.soa.srv.demo.bean.dto.UserUpdateDto;
import com.lfd.soa.srv.demo.service.SysUserService;
import com.lfd.soa.srv.demo.support.apivalidator.annotation.ApiValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author linfengda
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserCtrl {

    @Resource
    private SysUserService sysUserService;


    @PostMapping("/getUserInfo")
    public Result getUserInfo(@NotNull(message = "用户id不能为空") Integer userId) throws Exception {
        return new Result(sysUserService.getUserInfo(userId));
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@ApiValidator @RequestBody UserUpdateDto userUpdateDTO) throws Exception {
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
