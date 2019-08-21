package com.linfengda.sb.chapter1.system.api;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.linfengda.sb.chapter1.system.entity.dto.UserDTO;
import com.linfengda.sb.chapter1.system.entity.vo.UserListVO;
import com.linfengda.sb.chapter1.system.service.SystemService;
import com.linfengda.sb.chapter1.system.service.TransactionalService;
import com.linfengda.sb.support.api.BaseController;
import com.linfengda.sb.support.api.entity.RequestParam;
import com.linfengda.sb.support.api.entity.Result;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述: 测试MVC
 *
 * @author linfengda
 * @create 2018-08-16 10:29
 */
@RestController
public class SystemController extends BaseController {
    @Resource
    private TransactionalService transactionalService;
    @Resource
    private SystemService systemService;

    @PostMapping("/sys/pageUserList")
    public Result pageUserList() throws Exception {
        RequestParam params = getParams();
        Assert.notNull(params.getInteger("pageNo"), "分页页码必须为数字且不能为空");
        Assert.notNull(params.getInteger("pageSize"), "分页大小必须为数字且不能为空");
        Page<UserListVO> userListVOPage = systemService.pageUserList(params);
        return new Result(userListVOPage);
    }

    @PostMapping("/sys/getUserInfo")
    public Result getUserInfo() throws Exception {
        RequestParam params = getParams();
        Assert.notNull(params.getLong("userId"), "用户ID不能为空");
        return new Result(systemService.getUserInfo(params.getLong("userId")));
    }

    @PostMapping("/sys/updateUser")
    public Result updateUser() throws Exception {
        RequestParam params = getParams();
        UserDTO userDTO = JSON.toJavaObject(params, UserDTO.class);
        systemService.updateUser(userDTO.getUserId(), userDTO.getUserName());
        return SUCCESS_RESULT;
    }
}
